package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.Day
import org.springframework.data.jpa.repository.JpaRepository

interface DayRepository : JpaRepository<Day,Long> {
    fun findAllByWorkoutProgramId(id: Long): List<Day>
}