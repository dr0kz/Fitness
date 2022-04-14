package com.sorsix.fitness.config.payload

data class LoginRequest(
    var email: String? = null,
    var password: String? = null
)