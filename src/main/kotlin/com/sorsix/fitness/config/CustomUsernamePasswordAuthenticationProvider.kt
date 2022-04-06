package com.sorsix.fitness.config

import com.sorsix.fitness.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class CustomUsernamePasswordAuthenticationProvider(val userService: UserService, val passwordEncoder: PasswordEncoder) :AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val email: String = authentication.name
        val password: String = authentication.credentials.toString()
        if ("" == email || "" == password) {
            throw BadCredentialsException("Invalid Credentials")
        }
        val userDetails: UserDetails = userService.loadUserByUsername(email)

        if (!passwordEncoder.matches(password, userDetails.password)) {
            throw BadCredentialsException("Password is incorrect!")
        }
        return UsernamePasswordAuthenticationToken(userDetails, userDetails.password, userDetails.authorities)
    }

    override fun supports(aClass: Class<*>): Boolean {
        return aClass == UsernamePasswordAuthenticationToken::class.java
    }
}