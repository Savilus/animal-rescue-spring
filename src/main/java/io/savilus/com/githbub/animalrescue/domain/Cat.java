package io.savilus.com.githbub.animalrescue.domain;

import lombok.Value;

@Value
public class Cat implements Animal{

    String id;
    String name;
    Integer age;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public Specie getSpecie() {
        return Specie.CAT;
    }
}
