package com.sorsix.fitness.config.payload

data class LoginRequest(
    var username: String? = null, //email
    var password: String? = null
)