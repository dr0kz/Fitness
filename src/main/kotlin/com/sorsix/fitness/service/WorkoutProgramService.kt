package com.sorsix.fitness.service

import com.sorsix.fitness.domain.entities.WorkoutProgram

import com.sorsix.fitness.api.dto.BadRequest
import com.sorsix.fitness.api.dto.NotFound
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.api.dto.response.WorkoutProgramAndDaysResponse
import com.sorsix.fitness.api.dto.workout_program.DayRequest
import com.sorsix.fitness.domain.entities.BoughtProgram
import com.sorsix.fitness.domain.entities.Day
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.enum.DayOfWeek
import com.sorsix.fitness.domain.enum.Role
import com.sorsix.fitness.repository.BoughtProgramRepository
import com.sorsix.fitness.repository.DayRepository
import com.sorsix.fitness.repository.UserRepository

import com.sorsix.fitness.repository.WorkoutProgramRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.IntStream
import kotlin.math.ceil

@Service
class WorkoutProgramService(
    val workoutProgramRepository: WorkoutProgramRepository,
    val userRepository: UserRepository,
    val dayRepository: DayRepository,
    val boughtProgramRepository: BoughtProgramRepository,
    val daysRepository: DayRepository,
) {

    fun findAllByUserId(userId: Long): Response<*> {
        val user = this.userRepository.findById(userId)
        if (user.isEmpty) {
            NotFound("User with id $userId was not found")
        }
        return if (user.get().role == Role.TRAINEE) {
            val workoutProgramAndDaysResponses: MutableList<WorkoutProgramAndDaysResponse> = mutableListOf()
            val workoutPrograms = this.boughtProgramRepository.findAllByUserId(userId)
            workoutPrograms.forEach{t ->
                val days = this.daysRepository.findAllByWorkoutProgramId(t.id)
                val workoutProgramAndDaysResponse = WorkoutProgramAndDaysResponse(t,days)
                workoutProgramAndDaysResponses.add(workoutProgramAndDaysResponse)
            }
            Success(workoutProgramAndDaysResponses)
        } else {
            val workoutProgramAndDaysResponses: MutableList<WorkoutProgramAndDaysResponse> = mutableListOf()
            val workoutPrograms = this.workoutProgramRepository.findAllByUserTrainerId(userId)
            workoutPrograms.forEach{t ->
                val days = this.daysRepository.findAllByWorkoutProgramId(t.id)
                val workoutProgramAndDaysResponse = WorkoutProgramAndDaysResponse(t,days)
                workoutProgramAndDaysResponses.add(workoutProgramAndDaysResponse)
            }
            Success(workoutProgramAndDaysResponses)
        }
    }

    fun findById(workoutProgramId: Long): Response<*> {
        val workoutProgram: Optional<WorkoutProgram> = this.workoutProgramRepository.findById(workoutProgramId)
        if (workoutProgram.isEmpty) {
            return NotFound("Workout program with id $workoutProgramId was not found")
        }
        return Success(workoutProgram.get())
    }

    fun create(
        name: String,
        price: Int,
        description: String,
        days: List<DayRequest>
    ): Response<*> {
        val user = SecurityContextHolder.getContext().authentication.principal as User


        val workoutProgram =
            WorkoutProgram(name = name, price = price, description = description, userTrainer = user)

        this.workoutProgramRepository.save(workoutProgram)

        IntStream.range(0, days.size).forEach { k ->
            val day: Day = days[k].let { t ->
                Day(
                    dayOfWeek = DayOfWeek.fromInt((k) % 7),
                    title = t.title,
                    description = t.description, video = t.video,
                    workoutProgram = workoutProgram,
                    week = ceil((k + 1).toDouble() / 7).toInt()
                )
            }
            this.dayRepository.save(day)
        }

        return Success(workoutProgram)
    }

    fun delete(id: Long): Response<*> {
        val workoutProgram = this.workoutProgramRepository.findById(id)
        if (workoutProgram.isEmpty) {
            return BadRequest("Workout program with id $id was not found")
        }
        this.workoutProgramRepository.delete(workoutProgram.get())
        return Success(workoutProgram.get())
    }

    fun buy(id: Long): Response<*> {
        val workoutProgram = this.workoutProgramRepository.findById(id)
        if (workoutProgram.isEmpty) {
            return BadRequest("Workout program with id $id was not found")
        }
        val boughtProgram = BoughtProgram(user = User(), workoutProgram = workoutProgram.get())
        this.boughtProgramRepository.save(boughtProgram)
        return Success(boughtProgram)
    }
}