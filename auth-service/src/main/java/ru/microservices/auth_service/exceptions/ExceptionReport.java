package ru.microservices.auth_service.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
public class ExceptionReport {

    @JsonProperty(index = 1)
    private String error;
    @JsonProperty(index = 2)
    private HttpStatus httpStatus;
    @JsonProperty(index = 3)
    private Integer statusCode;
    @JsonProperty(index = 4)
    private String message;
    @JsonProperty(index = 5)
    private Instant occurredAt;
}
