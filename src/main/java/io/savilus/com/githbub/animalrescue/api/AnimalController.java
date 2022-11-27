package io.savilus.com.githbub.animalrescue.api;

import io.savilus.com.githbub.animalrescue.api.dto.AllAnimalsResponse;
import io.savilus.com.githbub.animalrescue.api.dto.CreateAnimalRequest;
import io.savilus.com.githbub.animalrescue.api.dto.ErrorDto;
import io.savilus.com.githbub.animalrescue.api.dto.SingleAnimalResponse;
import io.savilus.com.githbub.animalrescue.domain.Animal;
import io.savilus.com.githbub.animalrescue.domain.Specie;
import io.savilus.com.githbub.animalrescue.infastructure.AnimalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.Arrays;

// RestControler -> @Controller i @ResponseBody w jednym
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping(path = "/animals")
    public AllAnimalsResponse getAnimals(@RequestParam(required = false, defaultValue = "3") Integer limit) {
        AllAnimalsResponse animalsResponse = new AllAnimalsResponse(
                animalService.listOfAnimals(limit));
        return animalsResponse;
    }

    @GetMapping(path = "/animals/{id}")
    public SingleAnimalResponse getById(@PathVariable String id) {
        log.info(id);
        return new SingleAnimalResponse(
                animalService.singleAnimal(id).getAge(),
                animalService.singleAnimal(id).getSpecie());
    }

    @PostMapping(path = "/animals/{specie}") // pobieramy z frontu i tworzymy zasoby
    public ResponseEntity<Animal> addAnimal(
            @Valid
            @RequestBody CreateAnimalRequest request,
            @PathVariable String specie) {
        log.info(request.toString());
        log.info(specie);
        Animal animal = animalService.createAnimal(parseStringToSpecie(specie), request.getAge(), request.getName());
        return ResponseEntity.created(URI.create("/animals")).body(animal);
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
    // exception handler - wywołuje metode w przypadku wykrycia błedu
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception){
        log.info(exception.getBindingResult().toString());
        return ResponseEntity.badRequest().body(new ErrorDto("", Instant.now(),HttpStatus.BAD_REQUEST));
    }
}

