package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.service.DayService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/days")
class DayController(val dayService : DayService) {

    @GetMapping("/{workoutProgramId}")
    fun findAllByWorkoutProgramId(@PathVariable workoutProgramId: Long) =
        this.dayService.findAllByWorkoutProgramId(workoutProgramId)


}
