package com.sorsix.fitness.api.dto

sealed interface Response<T>

data class Success<T>(val result: T) : Response<T>
data class BadRequest(val result: String) : Response<String>
data class NotFound(val result: String) : Response<String>