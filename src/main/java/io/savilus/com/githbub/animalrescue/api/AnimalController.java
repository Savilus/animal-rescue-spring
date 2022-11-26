package io.savilus.com.githbub.animalrescue.api;

import io.savilus.com.githbub.animalrescue.api.dto.AllAnimalsResponse;
import io.savilus.com.githbub.animalrescue.api.dto.SingleAnimalResponse;
import io.savilus.com.githbub.animalrescue.domain.Dog;
import io.savilus.com.githbub.animalrescue.infastructure.AnimalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @PostMapping(path = "/animals")
    public ResponseEntity<Dog> addAnimal(@RequestBody Dog dog) {
        log.info(dog.toString());
        return ResponseEntity.created(URI.create("/animals")).build();
    }

}

