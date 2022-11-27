package io.savilus.com.githbub.animalrescue.api.dto;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Value
public class CreateAnimalRequest {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    @Length(message = "Name have to be between 2 or 30", min = 2, max = 30)
    String name;

    @NotNull(message = "Age cannot be null")
    @Positive(message = "Age must be positive")
    @Max(message = "Max age is 200", value = 200)
    Integer age;




}
