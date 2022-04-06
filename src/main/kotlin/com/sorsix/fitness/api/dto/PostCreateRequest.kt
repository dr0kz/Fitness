package com.sorsix.fitness.api.dto

data class PostCreateRequest(
    val description: String,
    val image: String,
    val userId: Long
)
