package io.savilus.com.githbub.animalrescue.domain;

public enum Specie {


    DOG("dogs"),
    CAT("cats"),
    ELEPHANT("elephants");

    private final String pluralValue;

    Specie(String pluralValue) {
        this.pluralValue = pluralValue;
    }

    public String getPluralValue() {
        return pluralValue;
    }
}
