package com.sorsix.fitness.api.controller

import com.sorsix.fitness.api.dto.*
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/users")
class UserController(val userService: UserService) {

    @PostMapping("/login")
    fun loginUser(request: HttpServletRequest, @RequestBody loginRequest: LoginRequest) {

    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest) : ResponseEntity<Response<*>> =
        with(registerRequest){
            when(val registerResponse = userService.register(email, name, surname, role, password, confirmPassword)){
                is Success -> ResponseEntity.ok(Success(registerResponse.result))
                is BadRequest -> ResponseEntity.badRequest().body(BadRequest(registerResponse.result))
                is NotFound -> ResponseEntity(NotFound(registerResponse.result),HttpStatus.NOT_FOUND)
            }

        }
}