package ru.microservices.auth_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.microservices.auth_service.models.UserRequest;
import ru.microservices.auth_service.service.AuthService;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody UserRequest userRequest){
        return authService.createUser(userRequest);
    }
}
