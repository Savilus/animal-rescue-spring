package io.savilus.com.githbub.animalrescue.infastructure;

import io.savilus.com.githbub.animalrescue.domain.*;
import io.savilus.com.githbub.animalrescue.repositories.AnimalsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DefaultAnimalService implements AnimalService {

    private final AnimalsRepository animalsRepository;

    public DefaultAnimalService(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }


    @Override
    public List<Animal> listOfAnimals(Integer limit) {
        return animalsRepository.findAllAnimals(limit);
    }

    @Override
    public Animal singleAnimal(String id) {
        return animalsRepository.findAnimal(id);
    }

    @Override
    public Animal createAnimal(Specie specie, Integer age, String name) {
        // losowy generator, bardzo mała kolizyjność
        UUID uuid = UUID.randomUUID();
        Animal animal;

        switch (specie) {
            case DOG -> {
                animal = new Dog(uuid.toString(), name, age);
            }
            case CAT -> {
                animal = new Cat(uuid.toString(), name, age);
            }
            case ELEPHANT -> {
                animal = new Elephant(uuid.toString(), name, age);
            }
            default -> {
                throw new IllegalStateException("Unsupported specie ");
            }

        }

        animalsRepository.saveAnimal(animal);
        return animal;
    }

    @Override
    public boolean deleteAnimal(String id) {
        animalsRepository.deleteAnimal(id);
        log.info("Deleting animal by ID {}", id);
        return true;
    }
}
