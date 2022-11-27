package io.savilus.com.githbub.animalrescue.api.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
public class CreateAnimalRequest {
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    String name;

    @NotNull(message = "Age cannot be null")
    @Positive(message = "Age must be positive")
    Integer age;


}
