package com.sorsix.fitness.config.payload

import com.sorsix.fitness.domain.enum.Role

data class SignupRequest(
    val email: String? = null, //username

    val role: Role? = null,

    val password: String? = null,

    val confirmPassword: String? = null
)