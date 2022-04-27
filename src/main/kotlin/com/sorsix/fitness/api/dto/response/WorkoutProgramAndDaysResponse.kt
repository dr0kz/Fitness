package com.sorsix.fitness.api.dto.response

import com.sorsix.fitness.domain.entities.Day
import com.sorsix.fitness.domain.entities.WorkoutProgram

data class WorkoutProgramAndDaysResponse(
    val workoutProgram: WorkoutProgram,
    val days: List<Day>
)