package io.savilus.com.githbub.animalrescue.api;

import io.savilus.com.githbub.animalrescue.api.dto.AllAnimalsResponse;
import io.savilus.com.githbub.animalrescue.api.dto.SingleAnimalResponse;
import io.savilus.com.githbub.animalrescue.domain.Animal;
import io.savilus.com.githbub.animalrescue.domain.Dog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping
public class AnimalController {

    private final Map<String, Animal> animals = Map.of(
            "1", new Dog("1","krecik", 17),
            "2", new Dog("2","reksio", 12),
            "3", new Dog("3","nutka", 8),
            "4", new Dog("4","minutka", 9));

    @GetMapping(path = "/animals")
    public AllAnimalsResponse getAnimals() {
        AllAnimalsResponse animalResponse = new AllAnimalsResponse(animals.values().stream().toList());
        return animalResponse;
    }

    @GetMapping(path = "/animals/{name}")
    public SingleAnimalResponse getById(@PathVariable String name) {
        return new SingleAnimalResponse(
                animals.get(name.toLowerCase()).getAge(),
                animals.get(name.toLowerCase()).getSpecie());
    }

}

