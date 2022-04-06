package com.sorsix.fitness.api.dto

data class WorkoutProgramReq (
    val name: String,
    val price: Int,
    val description: String,
    val weeks: List<DayReq>
)