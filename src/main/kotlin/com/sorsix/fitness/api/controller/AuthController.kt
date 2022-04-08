package com.sorsix.fitness.api.controller

import com.sorsix.fitness.config.JwtUtils
import com.sorsix.fitness.config.PasswordEncoder
import com.sorsix.fitness.config.UserDetailsImpl
import com.sorsix.fitness.config.payload.JwtResponse
import com.sorsix.fitness.config.payload.LoginRequest
import com.sorsix.fitness.config.payload.MessageResponse
import com.sorsix.fitness.config.payload.SignupRequest
import com.sorsix.fitness.domain.entities.Role
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.repository.RoleRepository
import com.sorsix.fitness.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import java.util.stream.Collectors


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authenticationManager: AuthenticationManager,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
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
    fun registerUser(@RequestBody signUpRequest: SignupRequest): ResponseEntity<*> {
        if (signUpRequest.email?.let { userRepository.existsByEmail(it) } == true) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository!!.existsByEmail(signUpRequest.email!!)!!) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Email is already in use!"))
        }

        // Create new user's account
        val user = signUpRequest.username?.let {
            User(0L,
                name="d",
                surname="x",

                it,
                signUpRequest.email!!,
                encoder.passwordEncoderBean().encode(signUpRequest.password)
            )
        }
        val strRoles = signUpRequest.role
        val roles: MutableSet<Role> = HashSet<Role>()
        if (strRoles == null) {
            val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
                .orElseThrow { RuntimeException("Error: Role is not found.") }
            roles.add(userRole)
        } else {
            strRoles.forEach(Consumer { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole: Role = roleRepository!!.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            }
                        roles.add(adminRole)
                    }
                    "mod" -> {
                        val modRole: Role = roleRepository!!.findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            }
                        roles.add(modRole)
                    }
                    else -> {
                        val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
                            .orElseThrow {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            }
                        roles.add(userRole)
                    }
                }
            })
        }
        user.setRoles(roles)
        userRepository!!.save<User>(user)
        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}