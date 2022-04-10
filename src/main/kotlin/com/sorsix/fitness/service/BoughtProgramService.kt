package com.sorsix.fitness.service

import com.sorsix.fitness.api.dto.BadRequest
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.WorkoutProgram
import com.sorsix.fitness.repository.BoughtProgramRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service
class BoughtProgramService(val boughtProgramRepository: BoughtProgramRepository) {

    fun listAllByUser(): List<WorkoutProgram> {
        val principal = SecurityContextHolder.getContext().authentication.principal as User
        return this.boughtProgramRepository.findAllByUserId(principal.id)
    }


    fun findByWorkoutProgramId(id: Long): Response<*> {
        //user
        val workoutProgram = this.boughtProgramRepository.findByUserIdAndWorkoutProgramId(1, id)
        if (workoutProgram.isEmpty) {
            return BadRequest("Workout program with id $id was not found")
        }
        return Success(workoutProgram.get())
    }

}