package io.savilus.com.githbub.animalrescue.api;

import io.savilus.com.githbub.animalrescue.api.dto.AllAnimalsResponse;
import io.savilus.com.githbub.animalrescue.api.dto.CreateAnimalRequest;
import io.savilus.com.githbub.animalrescue.api.dto.ErrorDto;
import io.savilus.com.githbub.animalrescue.domain.Animal;
import io.savilus.com.githbub.animalrescue.domain.Specie;
import io.savilus.com.githbub.animalrescue.infastructure.AnimalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// RestControler -> @Controller i @ResponseBody w jednym
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping(path = "/animals")
    public ResponseEntity<Page<Animal>> getAnimals(@RequestParam(required = false, defaultValue = "3") Integer limit,
                                                   @RequestParam Integer size,
                                                   @RequestParam Integer page,
                                                   @RequestParam(required = false, defaultValue = "age") String sort,
                                                   @RequestParam(required = false, defaultValue = "DESC") String direction) {

        Sort sortBy = Sort.by(Sort.Direction.fromString(direction), sort);
        PageRequest of = PageRequest.of(page, size, sortBy);
        return ResponseEntity.ok().body(animalService.listOfAnimals(of));
    }

    @GetMapping(path = "/animals/{id}")
    public ResponseEntity<Animal> getById(@PathVariable String id) {
        log.info(id);
        Animal singleAnimal = animalService.singleAnimal(id);
        if(singleAnimal == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(singleAnimal);
        }
    }

    @PostMapping(path = "/animals/{specie}") // pobieramy z frontu i tworzymy zasoby
    public ResponseEntity<Animal> addAnimal(
            @Valid
            @RequestBody CreateAnimalRequest request,
            @PathVariable String specie) {
        log.info(request.toString());
        log.info(specie);
        Animal animal = animalService.createAnimal(parseStringToSpecie(specie), request.getAge(), request.getName(), null);
        return ResponseEntity.created(URI.create("/animals")).body(animal);
    }

    @PutMapping(path = "/animals/{specie}/{id}")
    public ResponseEntity<Animal> upsertAnimal(
            @PathVariable String id,
            @PathVariable String specie,
            // sprawdza co przychodzi z requesta i tworzy na podstawie tego co mamy w body
            @RequestBody CreateAnimalRequest createAnimalRequest){
        log.info("Animal exist: {}", animalService.animalExist(id));
        Animal animal = animalService.createAnimal((
                parseStringToSpecie(specie)),
                createAnimalRequest.getAge(),
                createAnimalRequest.getName(),
                id
        );
        return ResponseEntity.ok().body(animal);
    }

    @DeleteMapping(path = "/animals/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable String id){
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    private Specie parseStringToSpecie(String rawSpecie){
        return Arrays.stream(Specie.values())
                .filter(specie -> specie.getPluralValue().equals(rawSpecie))
                .findFirst().get();
    }
    // exception handler - wywołuje metode w przypadku wykrycia błedu, jak poleci wyjątek i zwróci wartość jaką podam
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getAllErrors().
                forEach(s -> errorMap.put(((FieldError) s).getField(), s.getDefaultMessage()));
        String message = errorMap.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ErrorDto(message, Instant.now(), HttpStatus.BAD_REQUEST));

    }
}

