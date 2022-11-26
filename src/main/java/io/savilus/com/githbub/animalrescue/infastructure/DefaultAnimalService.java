package io.savilus.com.githbub.animalrescue.infastructure;

import io.savilus.com.githbub.animalrescue.domain.Animal;
import io.savilus.com.githbub.animalrescue.domain.Cat;
import io.savilus.com.githbub.animalrescue.domain.Dog;
import io.savilus.com.githbub.animalrescue.domain.Elephant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DefaultAnimalService implements AnimalService {

    private final Map<String, Animal> animals = Map.of(
            "1", new Dog("1", "krecik", 17),
            "2", new Dog("2", "reksio", 12),
            "3", new Dog("3", "nutka", 8),
            "4", new Dog("4", "minutka", 9),
            "5", new Cat("5", "mruczek", 10),
            "6", new Elephant("6", "stefan", 78),
            "7", new Cat("7","burek", 3));



    @Override
    public List<Animal> listOfAnimals(Integer limit) {
        return animals.values().stream().limit(limit.longValue()).toList();
    }

    @Override
    public Animal singleAnimal(String id) {
        return animals.get(id);
    }
}
