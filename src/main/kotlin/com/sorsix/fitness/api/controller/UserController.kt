package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.api.dto.*
import com.sorsix.fitness.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {


    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest) : ResponseEntity<RegisterResponse> =
        with(registerRequest){
            when(val registerResponse = userService.register(email, name, surname, role, password, confirmPassword)){
                is RegisterSuccessfully -> ResponseEntity.ok(RegisterSuccessfully(registerResponse.user))
                is EmailAlreadyExists -> ResponseEntity.badRequest().body(EmailAlreadyExists(registerResponse.message))
                is PasswordsDoNotMatch -> ResponseEntity.badRequest().body(PasswordsDoNotMatch(registerResponse.message))
            }

        }

}
