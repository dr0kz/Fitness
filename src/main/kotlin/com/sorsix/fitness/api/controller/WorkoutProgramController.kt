package com.sorsix.fitness.api.controller

import com.sorsix.fitness.api.dto.BadRequest
import com.sorsix.fitness.api.dto.NotFound
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.api.dto.workout_program.WorkoutProgramRequest
import com.sorsix.fitness.domain.entities.WorkoutProgram
import com.sorsix.fitness.service.WorkoutProgramService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/workout-program")
class WorkoutProgramController(val workoutProgramService: WorkoutProgramService) {

    @GetMapping("/list/{userId}")
    fun findAllByTrainer(@PathVariable userId: Long): ResponseEntity<Response<*>> =
        when (val result = this.workoutProgramService.findAllByUserId(userId)) {
            is Success -> ResponseEntity.ok(Success(result.result))
            is BadRequest -> ResponseEntity.badRequest().body(BadRequest(result.result))
            is NotFound -> ResponseEntity(NotFound(result.result), HttpStatus.NOT_FOUND)
        }

    @GetMapping("/{workoutProgramId}")
    fun findByTrainer(@PathVariable workoutProgramId: Long): ResponseEntity<Response<*>> =
        when (val result = this.workoutProgramService.findById(workoutProgramId)) {
            is Success -> ResponseEntity.ok(Success(result.result))
            is BadRequest -> ResponseEntity.badRequest().body(BadRequest(result.result))
            is NotFound -> ResponseEntity(NotFound(result.result), HttpStatus.NOT_FOUND)
        }

    @PostMapping("/create")
    fun create(@RequestBody workoutProgramRequest: WorkoutProgramRequest): ResponseEntity<Response<*>> =
        with(workoutProgramRequest) {
            when (val result = workoutProgramService.create(name, price, description, days)) {
                is Success -> ResponseEntity.ok(Success(result.result))
                is BadRequest -> ResponseEntity.badRequest().body(BadRequest(result.result))
                is NotFound -> ResponseEntity(NotFound(result.result), HttpStatus.NOT_FOUND)
            }
        }

    //TODO
    @PutMapping("/edit/{workoutProgramId}")
    fun edit(@PathVariable workoutProgramId: Long, @RequestBody workoutProgramRequest: WorkoutProgramRequest
    ): ResponseEntity<Response<*>> =
        with(workoutProgramRequest){
            when(val result = workoutProgramService.create(name,price,description,days)){
                is Success -> ResponseEntity.ok(Success(result.result))
                is BadRequest -> ResponseEntity.badRequest().body(BadRequest(result.result))
                is NotFound -> ResponseEntity(NotFound(result.result), HttpStatus.NOT_FOUND)
            }
        }

    @DeleteMapping("/delete/{workoutProgramId}")
    fun delete(@PathVariable workoutProgramId: Long): ResponseEntity<Response<*>> =
        when (val result = this.workoutProgramService.delete(workoutProgramId)) {
            is Success -> ResponseEntity.ok(Success(result.result))
            is BadRequest -> ResponseEntity.badRequest().body(BadRequest(result.result))
            is NotFound -> ResponseEntity(NotFound(result.result), HttpStatus.NOT_FOUND)
        }

    @PostMapping("/buy/{workoutProgramId}")
    fun buy(@PathVariable workoutProgramId: Long) =
        this.workoutProgramService.buy(workoutProgramId)

}
