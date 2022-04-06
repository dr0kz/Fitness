package com.sorsix.fitness.config.filters

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.sorsix.fitness.api.dto.UserDetailsDto
import com.sorsix.fitness.config.JwtAuthConstants
import com.sorsix.fitness.config.PasswordEncoder
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.exceptions.PasswordsDoNotMatchException
import com.sorsix.fitness.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val authenticationManag: AuthenticationManager,
    private val userDetailsService: UserService,
    private val passwordEncoder: PasswordEncoder) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        var creds: User? = null
        try {
            creds = ObjectMapper().readValue(request?.inputStream, User::class.java) //fix za ovoj error?
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if(creds == null)
            throw Exception("Invalid credentials")
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(creds.username) //tuka pagjase u java proveri i tuka
        if(!passwordEncoder.passwordEncoderBean().matches(creds.password,userDetails.password))
            throw PasswordsDoNotMatchException();
        return authenticationManag.authenticate(
            UsernamePasswordAuthenticationToken(userDetails.username,userDetails.password,userDetails.authorities)
        )
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val userDetails = authResult.principal as User
        val token = JWT.create()
            .withSubject(ObjectMapper().writeValueAsString(UserDetailsDto.of(userDetails)))
            .withExpiresAt(Date(System.currentTimeMillis() + JwtAuthConstants.EXPIRATION_TIME))
            .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET.toByteArray()));    //proveri da ne e getBytes od java pogresno ova
        response.addHeader(JwtAuthConstants.HEADER_STRING, JwtAuthConstants.TOKEN_PREFIX + token)
        response.writer.append(token)
    }

}