package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.BoughtProgram
import com.sorsix.fitness.domain.entities.WorkoutProgram
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface BoughtProgramRepository : JpaRepository<BoughtProgram,Long> {
    fun findByUserIdAndWorkoutProgramId(userId: Long, workoutProgramId: Long): Optional<BoughtProgram>

    @Query("select b.workoutProgram from BoughtProgram b where b.user.id = :userId")
    fun findAllByUserId(userId: Long): List<WorkoutProgram>

}