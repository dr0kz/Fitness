package com.sorsix.fitness.api.dto

import com.sorsix.fitness.domain.entities.User

sealed interface LoginResponse

data class LoginSuccessfully(val user: User) : LoginResponse
data class UserDoesNotExist(val message: String) : LoginResponse
