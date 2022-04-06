package com.sorsix.fitness.api.dto

import com.sorsix.fitness.domain.entities.User

sealed interface RegisterResponse

data class RegisterSuccessfully(val user: User) : RegisterResponse
data class EmailAlreadyExists(val message: String) : RegisterResponse
data class PasswordsDoNotMatch(val message: String) : RegisterResponse