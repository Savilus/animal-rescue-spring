package io.savilus.com.githbub.animalrescue.infastructure;

import io.savilus.com.githbub.animalrescue.domain.*;
import io.savilus.com.githbub.animalrescue.repositories.AnimalsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DefaultAnimalService implements AnimalService {

    private final AnimalsDao animalsDao;

    public DefaultAnimalService(AnimalsDao animalsRepository) {
        this.animalsDao = animalsRepository;
    }


    @Override
    public Page<Animal> listOfAnimals(Pageable pageable) {
        return animalsDao.findAnimals(pageable);
    }

    @Override
    public Animal singleAnimal(String id) {
        return animalsDao.findAnimal(id);
    }

    @Override
    public Animal createAnimal(Specie specie, Integer age, String name, String id) {
        // losowy generator, bardzo mała kolizyjność

        Animal animal;
        String animalId;

        if(id == null){
            animalId = UUID.randomUUID().toString();
        } else{
            animalId = id;
        }


        switch (specie) {
            case DOG -> {
                animal = new Dog(animalId, name, age);
            }
            case CAT -> {
                animal = new Cat(animalId, name, age);
            }
            case ELEPHANT -> {
                animal = new Elephant(animalId, name, age);
            }
            default -> {
                throw new IllegalStateException("Unsupported specie ");
            }

        }

        animalsDao.saveAnimal(animal);
        return animal;
    }

    @Override
    public boolean deleteAnimal(String id) {
        animalsDao.deleteAnimal(id);
        log.info("Deleting animal by ID {}", id);
        return true;
    }

    @Override
    public boolean animalExist(String id) {
        return animalsDao.findAnimal(id) != null;
    }
}
