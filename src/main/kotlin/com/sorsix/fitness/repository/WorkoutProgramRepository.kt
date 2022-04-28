package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.WorkoutProgram
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface WorkoutProgramRepository : JpaRepository<WorkoutProgram,Long> {
    @Transactional
    fun findAllByUserTrainerId(id: Long): List<WorkoutProgram>

    @Modifying
    @Transactional
    @Query("update WorkoutProgram p set p.isValid = false where p.id = :workoutProgramId")
    fun markAsDeleted(workoutProgramId: Long)
}