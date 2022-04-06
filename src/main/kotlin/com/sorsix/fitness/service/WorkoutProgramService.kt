package com.sorsix.fitness.service

import com.sorsix.fitness.domain.entities.WorkoutProgram
import com.sorsix.fitness.repository.WorkoutProgramRepository
import org.springframework.stereotype.Service

@Service
class WorkoutProgramService(val workoutProgramRepository: WorkoutProgramRepository) {

    fun findAllWorkoutPrograms() : List<WorkoutProgram> =
        this.workoutProgramRepository.findAll()

    fun findAllWorkoutProgramsByTrainer(trainerId: Long): List<WorkoutProgram> =
        this.workoutProgramRepository.findAllByUserTrainerId(trainerId)

    fun findWorkoutProgramByTrainer(workoutProgramId: Long,trainerId: Long): WorkoutProgram? =
        this.workoutProgramRepository.findWorkoutProgramByIdAndUserTrainerId(workoutProgramId,trainerId)

    fun deleteWorkoutProgram(workoutProgramId: Long, trainerId: Long) =
        this.workoutProgramRepository.deleteWorkoutProgramByIdAndUserTrainerId(workoutProgramId,trainerId)

//    fun createWorkoutProgram(trainerId: Long) {
//        this.workoutProgramRepository.
//    }

    fun updateWorkoutProgramInfo(workoutProgramId: Long, trainerId: Long,
                                 name: String, price: Int, description: String) {
        val workoutProgram = findWorkoutProgramByTrainer(workoutProgramId,trainerId)
        if (workoutProgram!=null) {
            this.workoutProgramRepository.updateInfo(workoutProgramId,trainerId,name,price,description)
        }
    }

//    fun updateWorkoutProgramBody(workoutProgramId: Long, trainerId: Long) {
//
//    }
    
}