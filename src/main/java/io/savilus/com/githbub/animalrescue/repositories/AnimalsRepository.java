package io.savilus.com.githbub.animalrescue.repositories;

import io.savilus.com.githbub.animalrescue.domain.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalsRepository extends MongoRepository<Animal, String> {

    Animal findAnimalById(String id);
    Animal findAnimalByName(String name);
}
