package io.savilus.com.githbub.animalrescue.infastructure;

import io.savilus.com.githbub.animalrescue.domain.Animal;
import io.savilus.com.githbub.animalrescue.domain.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalService {

    Page<Animal> listOfAnimals(Pageable pageable);

    Animal singleAnimal(String id);
    Animal createAnimal(Specie specie, Integer age, String name, String id);
    boolean deleteAnimal(String id);
    boolean animalExist(String id);


}
