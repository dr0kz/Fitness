package com.sorsix.fitness.api.controller

import com.sorsix.fitness.config.payload.RegisterRequest
import com.sorsix.fitness.config.security.JwtUtils
import com.sorsix.fitness.config.security.PasswordEncoder
import com.sorsix.fitness.config.payload.JwtResponse
import com.sorsix.fitness.config.payload.LoginRequest
import com.sorsix.fitness.config.payload.MessageResponse
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.enum.Role
import com.sorsix.fitness.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authenticationManager: AuthenticationManager,
    val userRepository: UserRepository,
    val encoder: PasswordEncoder,
    val jwtUtils: JwtUtils
) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        try{
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            val jwt = jwtUtils.generateJwtToken(authentication)
            val userDetails = authentication.principal as User
            val role: String = userDetails.role.toString()

            return ResponseEntity.ok(
                JwtResponse(
                    jwt,
                    userDetails.id,
                    userDetails.email,
                    userDetails.name,
                    userDetails.surname,
                    userDetails.description,
                    userDetails.image,
                    role,
                    userDetails.followingNum,
                    userDetails.followersNum,
                )
            )
        }catch (ex: Exception){
            return ResponseEntity.badRequest().body(ex.message)
        }
    }

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody signUpRequest: RegisterRequest): ResponseEntity<*> {

        if (signUpRequest.email?.let { userRepository.existsByEmail(it) } == true) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Email is already taken!"))
        }
        if(signUpRequest.password != signUpRequest.confirmPassword){
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Passwords do not match"))
        }

        val user = User(
                0L,
                name = signUpRequest.name,
                surname = signUpRequest.surname,
                email = signUpRequest.email,
                password = encoder.passwordEncoderBean().encode(signUpRequest.password),
                role =  Role.valueOf(signUpRequest.role),
            )

        userRepository.save<User>(user)
        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}