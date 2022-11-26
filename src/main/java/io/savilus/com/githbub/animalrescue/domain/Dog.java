package io.savilus.com.githbub.animalrescue.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@EqualsAndHashCode
@ToString
// value - dodaje konstruktor i robi pola private final
@Value
public class Dog implements Animal{

    String id;
    String name;
    Integer age;



    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return "Dog " + name;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public Specie getSpecie() {
        return Specie.DOG;
    }
}
