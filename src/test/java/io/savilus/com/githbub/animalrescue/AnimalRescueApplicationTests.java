package io.savilus.com.githbub.animalrescue;

import io.savilus.com.githbub.animalrescue.api.dto.AllAnimalsResponse;
import io.savilus.com.githbub.animalrescue.api.dto.ErrorDto;
import io.savilus.com.githbub.animalrescue.api.dto.SingleAnimalResponse;
import io.savilus.com.githbub.animalrescue.domain.Dog;
import io.savilus.com.githbub.animalrescue.domain.Specie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalRescueApplicationTests {

    // musimy ustawić webEnviroment.Random port żeby korzystać z restRestTemplate
    // RestTemplate służy do symulacji postmana, za jej pomocy możemy robić requesty w testach
    // sprawdzamy czy zachowanie resta jest jak powinno być
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenApplicationStartShouldCreateRestTemplate() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void whenApplicationStartShouldReturnEmptyListOfAnimals() {
        // pamietać o noArgsConstructor
        //given
        String getAllAnimalsUrl = "/animals";
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Integer expectedAnimalCount = 0;
        // when
        ResponseEntity<AllAnimalsResponse> animalEntity =
                restTemplate.getForEntity("/animals", AllAnimalsResponse.class);

        // then
        assertThat(animalEntity.getStatusCode()).isEqualTo(expectedStatusCode);
        assertThat(animalEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(animalEntity.hasBody()).isTrue();
        assertThat(animalEntity.getBody().getAnimals().isEmpty()).isTrue();
        assertThat(animalEntity.getBody().getTotalAnimals()).isEqualTo(expectedAnimalCount);
    }

    @Test
    void whenUserAddDogApplicationShouldReturnThisAnimalAndListOfAnimalIsNotEmpty() {
        // given
        // headery mają oficjalne wartości które są wspierane
        // aby stringa odczytał jako json musimy przejśc przez httpheaders
        String animalBody = """
                {"name": "Reksio",
                "age": 10
                }
                """;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(animalBody, httpHeaders);
        String createAnimalEndpoint = "/animals/dogs";
        String getDogUrl;
        Integer expectedDogAge = 10;
        Specie expectedSpecie = Specie.DOG;
        HttpStatus expectedStatusCode = HttpStatus.CREATED;

        //create dog
        ResponseEntity<Dog> addDog =
                restTemplate.postForEntity(createAnimalEndpoint, stringHttpEntity, Dog.class);
        String dogId = addDog.getBody().getId();
        getDogUrl = "/animals/" + dogId;
        ResponseEntity<SingleAnimalResponse> allDogsEntity
                = restTemplate.getForEntity(getDogUrl, SingleAnimalResponse.class);
        //dog created
        assertThat(addDog.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(allDogsEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(allDogsEntity.getBody().getAge()).isEqualTo(expectedDogAge);
        assertThat(allDogsEntity.getBody().getSpecie()).isEqualTo(expectedSpecie);

    }
    @ParameterizedTest
    @MethodSource("badRequestArguments")
    void shouldReturnBadRequestWhenAgeIsNegative(String name, Integer age, String message){
        // given
        String animalBody = """
                {"name": "%s",
                "age": %d
                }
                """;
        String formattedAnimalBody = String.format(animalBody, name, age);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(formattedAnimalBody, httpHeaders);
        String postAnimalUrl = "/animals/dogs";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        //when
        ResponseEntity<ErrorDto> stringResponseEntity =
                restTemplate.postForEntity(postAnimalUrl, stringHttpEntity, ErrorDto.class);
        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(expectedStatus);
        assertThat(stringResponseEntity.getBody().getMessage()).isEqualTo(message);
    }

    static Stream<Arguments> badRequestArguments(){
      return Stream.of(
              Arguments.of("Reksio", null , "age: Age cannot be null"),
              Arguments.of("R", 10, "name: Name have to be between 2 or 30"),
              Arguments.of("Burek", 2500, "age: Max age is 200"),
              Arguments.of("    ", 10, "name: Name cannot be empty"),
              Arguments.of("Nutka", null, "age: Age cannot be null"),
              Arguments.of("Minutka", -3, "age: Age must be positive"));
    }




}
