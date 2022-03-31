package com.sorsix.fitness.service

import com.sorsix.fitness.domain.entities.WorkoutProgram
import com.sorsix.fitness.repository.WorkoutProgramRepository
import org.springframework.stereotype.Service

@Service
class WorkoutProgramService(val workoutProgramRepository: WorkoutProgramRepository) {

    fun findAllWorkoutPrograms() : List<WorkoutProgram>{
        return this.workoutProgramRepository.findAll()
    }

    fun findAllWorkoutProgramsByTrainer(trainerId: Long): List<WorkoutProgram> {
        return this.workoutProgramRepository.findAllByUserTrainerId(trainerId)
    }

    fun findWorkoutProgramByTrainer() {
        TODO()
    }

}