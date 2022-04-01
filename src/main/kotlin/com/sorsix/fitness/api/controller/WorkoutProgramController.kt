package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.api.dto.WorkoutProgramBodyReq
import com.sorsix.fitness.api.dto.WorkoutProgramInfoReq
import com.sorsix.fitness.api.dto.WorkoutProgramReq
import com.sorsix.fitness.domain.entities.WorkoutProgram
import com.sorsix.fitness.service.UserService
import com.sorsix.fitness.service.WorkoutProgramService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/workout-program")
class WorkoutProgramController(val workoutProgramService: WorkoutProgramService,
                               val userService: UserService,) {

    @GetMapping("/list")
    fun getAllWorkoutPrograms(): List<WorkoutProgram> =
        this.workoutProgramService.findAllWorkoutPrograms();

    @GetMapping("/list/{trainerId}")
    fun getAllWorkoutProgramsByTrainer(@PathVariable trainerId: Long): List<WorkoutProgram> =
        this.workoutProgramService.findAllWorkoutProgramsByTrainer(trainerId);

    @GetMapping("/{workoutProgramId}/{trainerId}")
    fun getWorkoutProgramByTrainer(@PathVariable workoutProgramId: Long,
                                   @PathVariable trainerId: Long): ResponseEntity<WorkoutProgram> =
        this.workoutProgramService.findWorkoutProgramByTrainer(workoutProgramId,trainerId)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{workoutProgramId}/{trainerId}")
    fun deleteWorkoutProgram(@PathVariable workoutProgramId: Long,
                             @PathVariable trainerId: Long) =
        this.workoutProgramService.deleteWorkoutProgram(workoutProgramId,trainerId)

//    @PostMapping("/{trainerId}")
//    fun createWorkoutProgram(@PathVariable trainerId: Long,
//                             @RequestBody workoutProgramReq : WorkoutProgramReq) {
//        TODO()
//    }

    @PutMapping("/{workoutProgramId}/{trainerId}")
    fun updateWorkoutProgramInfo(@PathVariable workoutProgramId: Long,
                                 @PathVariable trainerId: Long,
                                 @RequestBody workoutProgramInfoReq : WorkoutProgramInfoReq) =
        with(workoutProgramInfoReq) {
            workoutProgramService.updateWorkoutProgramInfo(workoutProgramId,trainerId,name,price,description)
        }

//    @PutMapping("/{workoutProgramId}/{trainerId}")
//    fun updateWorkoutProgramBody(@PathVariable workoutProgramId: Long,
//                                 @PathVariable trainerId: Long,
//                                 @RequestBody workoutProgramBodyReq : WorkoutProgramBodyReq){
//        TODO()
//    }

}
