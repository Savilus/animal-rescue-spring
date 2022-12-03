package io.savilus.com.githbub.animalrescue.repositories;

import io.savilus.com.githbub.animalrescue.domain.Animal;
import lombok.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class MongoAnimalDao implements AnimalsDao{

    private final AnimalsRepository animalsRepository;

    public MongoAnimalDao(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    @Override
    public void saveAnimal(Animal animal) {
        animalsRepository.save(animal);
    }

    @Override
    public Animal findAnimal(String id) {
        return animalsRepository.findAnimalById(id);

    }

    @Override
    public List<Animal> findAllAnimals(Integer limit) {
        return null;
    }

    @Override
    public void deleteAnimal(String id) {

    }
}
