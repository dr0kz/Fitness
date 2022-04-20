package com.sorsix.fitness.api.dto.projection

interface WorkoutProgramProjection {
    fun getId(): Long
    fun getName(): String
    fun getPrice(): Int
    fun getDescription(): String
}