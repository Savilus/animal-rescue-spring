package io.savilus.com.githbub.animalrescue.api.dto;

import io.savilus.com.githbub.animalrescue.domain.Specie;
import lombok.Value;

@Value
public class SingleAnimalResponse {

    Integer age;
    Specie specie;
}
