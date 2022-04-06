package com.sorsix.fitness.config.filters

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.sorsix.fitness.api.dto.UserDetailsDto
import com.sorsix.fitness.config.JwtAuthConstants
import com.sorsix.fitness.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(
    private val authenticationManag: AuthenticationManager,
    private val userDetailsService: UserService
) : BasicAuthenticationFilter(authenticationManag) {  //so ova vnatre authenticationManag mi barase

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(JwtAuthConstants.HEADER_STRING)
        if (header == null || !header.startsWith(JwtAuthConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }

        val token: UsernamePasswordAuthenticationToken? = getToken(header)
        SecurityContextHolder.getContext().authentication = token
        chain.doFilter(request, response)
    }

    @Throws(JsonProcessingException::class)
    private fun getToken(header: String): UsernamePasswordAuthenticationToken? {

        val user = JWT.require(Algorithm.HMAC256(JwtAuthConstants.SECRET.toByteArray()))
            .build()
            .verify(header.replace(JwtAuthConstants.TOKEN_PREFIX, ""))
            .subject ?: return null

        val userDetails: UserDetailsDto = ObjectMapper().readValue(user, UserDetailsDto::class.java)
        return UsernamePasswordAuthenticationToken(userDetails.username, "", setOf(userDetails.role)) //ova "" da ne pravi problem nesto
    }

}