package com.sorsix.fitness.service

import com.sorsix.fitness.api.dto.NotFound
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.domain.entities.BoughtProgram
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.WorkoutProgram
import com.sorsix.fitness.repository.BoughtProgramRepository
import com.sorsix.fitness.repository.WorkoutProgramRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class BoughtProgramService(
    val boughtProgramRepository: BoughtProgramRepository,
    val workoutProgramRepository: WorkoutProgramRepository) {

    fun findAllByUser(): List<WorkoutProgram> {
        val currentUser: User = SecurityContextHolder.getContext().authentication.principal as User
        return this.boughtProgramRepository.findAllByUserId(currentUser.id)
    }

    fun buy(id: Long): Response<*> {
        val currentUser: User = SecurityContextHolder.getContext().authentication.principal as User
        val workoutProgram = this.workoutProgramRepository.findById(id)
        if(workoutProgram.isEmpty){
            return NotFound("Workout program with id $id was not found")
        }
        this.boughtProgramRepository.save(BoughtProgram(user = currentUser, workoutProgram = workoutProgram.get()))
        return Success("Workout program ${workoutProgram.get().name} bought successfully")
    }
}