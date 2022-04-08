package com.sorsix.fitness.service


import com.sorsix.fitness.domain.entities.WorkoutProgram

import com.sorsix.fitness.api.dto.BadRequest
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.api.dto.workout_program.DayRequest
import com.sorsix.fitness.domain.entities.BoughtProgram
import com.sorsix.fitness.domain.entities.Day
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.WorkoutProgram
import com.sorsix.fitness.domain.enum.DayOfWeek
import com.sorsix.fitness.repository.BoughtProgramRepository
import com.sorsix.fitness.repository.DayRepository
import com.sorsix.fitness.repository.UserRepository

import com.sorsix.fitness.repository.WorkoutProgramRepository
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class WorkoutProgramService(
    val workoutProgramRepository: WorkoutProgramRepository,
    val userRepository: UserRepository,
    val dayRepository: DayRepository,
    val boughtProgramRepository: BoughtProgramRepository
) {

    fun findAllWorkoutProgramsByTrainerId(trainerId: Long) =
        this.workoutProgramRepository.findAllByUserTrainerId(trainerId)

    fun findById(workoutProgramId: Long): WorkoutProgram? =
        this.workoutProgramRepository.findById(workoutProgramId).orElse(null)

    fun create(
        name: String,
        price: Int,
        description: String,
        userTrainerId: Long,
        days: List<DayRequest>
    ): Response<*> {
        val user = this.userRepository.findById(userTrainerId)
        if (user.isEmpty) {
            return BadRequest("User with id $userTrainerId was not found")
        }
        val workoutProgram =
            WorkoutProgram(name = name, price = price, description = description, userTrainer = user.get())

        this.workoutProgramRepository.save(workoutProgram)

        days.stream().map { t ->
            println((days.indexOf(t)+1) % 7)
            Day(
                dayOfWeek = DayOfWeek.fromInt((days.indexOf(t)) % 7), title = t.title,
                description = t.description, video = t.video,
                workoutProgram = workoutProgram, week = ceil((days.indexOf(t)+1).toDouble() / 7).toInt()
            )
        }.forEach { this.dayRepository.save(it) }

        return Success(workoutProgram)
    }

    fun delete(id: Long) : Response<*>{
        val workoutProgram = this.workoutProgramRepository.findById(id)
        if(workoutProgram.isEmpty){
            return BadRequest("Workout program with id $id was not found")
        }
        this.workoutProgramRepository.delete(workoutProgram.get())
        return Success(workoutProgram.get())
    }

    fun buy(id: Long) : Response<*>{
        val workoutProgram = this.workoutProgramRepository.findById(id)
        if(workoutProgram.isEmpty){
            return BadRequest("Workout program with id $id was not found")
        }
        val boughtProgram = BoughtProgram(user = User(), workoutProgram = workoutProgram.get())
        this.boughtProgramRepository.save(boughtProgram)
        return Success(boughtProgram)
    }
}