package com.sorsix.fitness.api.dto

import com.sorsix.fitness.domain.enum.DayOfWeek

data class DayReq (
    val dayOfWeek: DayOfWeek,
    val title: String,
    val description: String,
    val video: String
)