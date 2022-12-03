package io.savilus.com.githbub.animalrescue.repositories;

import io.savilus.com.githbub.animalrescue.domain.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalsDao {

    void saveAnimal(Animal animal);

    Animal findAnimal(String id);

    List<Animal> findAllAnimals(Integer limit);

    void deleteAnimal(String id);

    Page<Animal> findAnimals(Pageable pageable);

}
