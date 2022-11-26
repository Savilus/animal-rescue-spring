package io.savilus.com.githbub.animalrescue.api.dto;

import io.savilus.com.githbub.animalrescue.domain.Animal;
import lombok.Value;

import java.util.List;

@Value
public class AllAnimalsResponse {

     List<Animal> animals;
     Integer totalAnimals;

    public AllAnimalsResponse(List<Animal> animals) {
        this.animals = animals;
        this.totalAnimals = animals.size();
    }
}
