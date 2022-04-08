package com.sorsix.fitness.api.controller

import com.sorsix.fitness.api.dto.RegisterRequest
import com.sorsix.fitness.config.JwtUtils
import com.sorsix.fitness.config.PasswordEncoder
import com.sorsix.fitness.config.UserDetailsImpl
import com.sorsix.fitness.config.payload.JwtResponse
import com.sorsix.fitness.config.payload.LoginRequest
import com.sorsix.fitness.config.payload.MessageResponse
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
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
    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils!!.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())
        return ResponseEntity.ok(
            JwtResponse(
                jwt,
                userDetails.id,
                userDetails.username,
                userDetails.email,
                roles
            )
        )
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: RegisterRequest): ResponseEntity<*> {

        if(signUpRequest.name==null){
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Name field is empty!"))
        }
        if(signUpRequest.surname==null){
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: surname field is empty!"))
        }
        if(signUpRequest.email==null){
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: email field is empty!"))
        }
        if(signUpRequest.role==null){
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: role field is empty!"))
        }
        if(signUpRequest.password==null){
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: password field is empty!"))
        }
        if(signUpRequest.confirmPassword==null){
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: confirmPassword field is empty!"))
        }
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

        // Create new user's account
        val user = User(
                0L,
                name = signUpRequest.name!!,
                surname = signUpRequest.surname!!,
                email = signUpRequest.email!!,
                password = encoder.passwordEncoderBean().encode(signUpRequest.password),
                role = signUpRequest.role,
            )

        userRepository.save<User>(user)

        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}