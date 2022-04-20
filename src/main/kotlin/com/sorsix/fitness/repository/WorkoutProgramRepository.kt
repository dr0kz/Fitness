package com.sorsix.fitness.repository

import com.sorsix.fitness.api.dto.projection.UserProjection
import com.sorsix.fitness.api.dto.projection.WorkoutProgramProjection
import com.sorsix.fitness.domain.entities.WorkoutProgram
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface WorkoutProgramRepository : JpaRepository<WorkoutProgram,Long> {
    @Modifying
    @Transactional
    @Query("update WorkoutProgram p set p.name = :name, p.price = :price, p.description = :description " +
            "where p.id = :workoutProgramId and p.userTrainer.id = :trainerId")
    fun updateInfo(workoutProgramId: Long, trainerId: Long, name: String, price: Int, description: String)

    @Transactional
    @Query("select wp.id as id, wp.name as name, wp.price as price, wp.description as description from WorkoutProgram wp" +
            " where wp.userTrainer.id = :id")
    fun findAllByUserTrainerId(id: Long): List<WorkoutProgramProjection>


}