package com.sorsix.fitness.config.payload

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class RegisterRequest(

    @field:NotNull
    @field:NotEmpty
    val email: String,

    @field:NotNull
    @field:NotEmpty
    val name: String,

    @field:NotNull
    @field:NotEmpty
    val surname: String,

    @field:NotNull
    val role: String,

    @field:NotNull
    @field:NotEmpty
    val password: String,

    @field:NotNull
    @field:NotEmpty
    val confirmPassword: String,
)
