package com.sorsix.fitness.api.dto.workout_program

data class WorkoutProgramRequest(
    val name: String,
    val price: Int,
    val description: String,
    val days: List<DayRequest>
)
