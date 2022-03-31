package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.WorkoutProgram
import org.springframework.data.jpa.repository.JpaRepository

interface WorkoutProgramRepository : JpaRepository<WorkoutProgram,Long> {
    fun findAllByUserTrainerId(userTrainerId: Long): List<WorkoutProgram>
}