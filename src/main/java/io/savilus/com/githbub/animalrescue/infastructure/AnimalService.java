package io.savilus.com.githbub.animalrescue.infastructure;

import io.savilus.com.githbub.animalrescue.domain.Animal;

import java.util.List;

public interface AnimalService {

    List<Animal> listOfAnimals(Integer limit);

    Animal singleAnimal(String id);


}
