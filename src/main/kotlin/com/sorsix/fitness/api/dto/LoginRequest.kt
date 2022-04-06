package com.sorsix.fitness.api.dto

data class LoginRequest(
    val email: String, //da bide val username: String mesto email
    val password: String
)
