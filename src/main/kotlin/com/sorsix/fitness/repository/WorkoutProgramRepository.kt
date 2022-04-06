package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.WorkoutProgram
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import javax.transaction.Transactional

interface WorkoutProgramRepository : JpaRepository<WorkoutProgram,Long> {
    @Modifying
    @Transactional
    @Query("update WorkoutProgram p set p.name = :name, p.price = :price, p.description = :description " +
            "where p.id = :workoutProgramId and p.userTrainer.id = :trainerId")
    fun updateInfo(workoutProgramId: Long, trainerId: Long, name: String, price: Int, description: String)


    fun findAllByUserTrainerId(id: Long)
}