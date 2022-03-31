package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.api.dto.WorkoutProgramReq
import com.sorsix.fitness.domain.entities.WorkoutProgram
import com.sorsix.fitness.service.UserService
import com.sorsix.fitness.service.WorkoutProgramService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/workout-program")
class WorkoutProgramController(val workoutProgramService: WorkoutProgramService,
                               val userService: UserService) {

    @GetMapping("/list")
    fun getAllWorkoutPrograms(): List<WorkoutProgram> {
        return this.workoutProgramService.findAllWorkoutPrograms();
    }

    @GetMapping("/list/{trainerId}")
    fun getAllWorkoutProgramsByTrainer(@PathVariable trainerId: Long): List<WorkoutProgram> {
        return this.workoutProgramService.findAllWorkoutProgramsByTrainer(trainerId);
    }

    @GetMapping("/{workoutProgramId}/{trainerId}")
    fun getWorkoutProgramByTrainer(@PathVariable workoutProgramId: Long,
                                   @PathVariable trainerId: Long): ResponseEntity<WorkoutProgram> {
        TODO()
    }

    @PostMapping("/{trainerId}")
    fun createWorkoutProgram(@PathVariable trainerId: Long,
                             @RequestBody workoutProgramReq : WorkoutProgramReq) {
        TODO()
    }

}
