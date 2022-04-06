package com.sorsix.fitness.api.dto.workout_program

import com.sorsix.fitness.domain.enum.DayOfWeek

data class DayRequest(
    val title: String = "",
    val description: String = "",
    val video: String = "",
)
