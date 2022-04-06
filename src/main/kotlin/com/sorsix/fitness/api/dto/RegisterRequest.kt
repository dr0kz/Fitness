package com.sorsix.fitness.api.dto

import com.sorsix.fitness.domain.enum.Role

data class RegisterRequest(
    val email: String,
    val name: String,
    val surname: String,
    val role: Role,
    val password: String,
    val confirmPassword: String,
)
