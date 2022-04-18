package com.sorsix.fitness.api.dto

data class EditProfileRequest(
    val email: String?,
    val name: String?,
    val surname: String?,
    val password: String?,
    val confirmPassword: String?,
    val description: String?
    //image?
)