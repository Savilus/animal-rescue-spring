package io.savilus.com.githbub.animalrescue.repositories;

import io.savilus.com.githbub.animalrescue.domain.Animal;

import java.util.List;

public interface AnimalsRepository {

    void saveAnimal(Animal animal);
    Animal findAnimal(String id);
    List<Animal> findAllAnimals(Integer limit);

    void deleteAnimal(String id);

}
