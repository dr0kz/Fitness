package com.sorsix.fitness.api.dto.projection

interface UserProjection {
    fun getId(): Long
    fun getName(): String
    fun getSurname(): String
    fun getImage(): ByteArray
}