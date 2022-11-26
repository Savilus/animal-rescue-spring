package io.savilus.com.githbub.animalrescue.infastructure;

import io.savilus.com.githbub.animalrescue.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DefaultAnimalService implements AnimalService {

    private final Map<String, Animal> animals = new HashMap<>();


    @Override
    public List<Animal> listOfAnimals(Integer limit) {
        return animals.values().stream().limit(limit.longValue()).toList();
    }

    @Override
    public Animal singleAnimal(String id) {
        return animals.get(id);
    }

    @Override
    public Animal createAnimal(Specie specie, Integer age, String name) {
        // losowy generator, bardzo mała kolizyjność
        UUID uuid = UUID.randomUUID();

        switch (specie) {
            case DOG -> {
                Animal animal = new Dog(uuid.toString(),name, age);
                animals.put(animal.getId(),animal);
                return animal;
            }
            case CAT -> {
                Animal animal = new Cat(uuid.toString(),name, age);
                animals.put(animal.getId(),animal);
                return animal;
            }
            case ELEPHANT -> {
                Animal animal = new Elephant(uuid.toString(), name, age);
                animals.put(animal.getId(),animal);
                return animal;
            }
            default -> {
                throw new IllegalStateException("Unsupported specie ");
            }
        }
    }

    @Override
    public boolean deleteAnimal(String id) {
        animals.remove(id);
        return true;
    }
}
