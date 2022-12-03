package io.savilus.com.githbub.animalrescue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.lang.reflect.Proxy;

@SpringBootApplication
@EnableMongoRepositories
public class AnimalRescueApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalRescueApplication.class, args);
    }


}
