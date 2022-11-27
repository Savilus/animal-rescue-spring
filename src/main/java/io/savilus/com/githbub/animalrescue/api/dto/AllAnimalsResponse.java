package io.savilus.com.githbub.animalrescue.api.dto;

import io.savilus.com.githbub.animalrescue.domain.Animal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
// dodajemy no args constructor dla testów
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AllAnimalsResponse {

     List<Animal> animals;
     Integer totalAnimals;

    public AllAnimalsResponse(List<Animal> animals) {
        this.animals = animals;
        this.totalAnimals = animals.size();
    }
}
