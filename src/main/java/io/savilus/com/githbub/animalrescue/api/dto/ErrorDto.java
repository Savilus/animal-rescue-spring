package io.savilus.com.githbub.animalrescue.api.dto;

import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Value
public class ErrorDto {

    String message;
    // instant w kazdym miejscu na świecie zwróci ten sam czas (+0)
    Instant timestamp;
    HttpStatus httpStatusCode;
}
