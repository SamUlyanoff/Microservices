package ru.microservices.auth_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionHandlerAuthService {

    @ExceptionHandler
    public ResponseEntity<ExceptionReport> handlePasswordValidationException(PasswordValidationException exception){
        ExceptionReport exceptionReport = ExceptionReport.builder()
                .error("Password Validation Error")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .occurredAt(Instant.now())
                .build();

        return new ResponseEntity<>(exceptionReport, HttpStatus.BAD_REQUEST);
    }
}
