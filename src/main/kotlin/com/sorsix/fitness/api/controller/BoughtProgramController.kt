package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.api.dto.BadRequest
import com.sorsix.fitness.api.dto.NotFound
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.service.BoughtProgramService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bought-programs")
class BoughtProgramController(val boughtProgramService: BoughtProgramService) {

    @GetMapping("/list")
    fun listAllByUser() =
        this.boughtProgramService.findAllByUser()

    @PostMapping("/buy/{workoutProgramId}")
    fun buy(@PathVariable workoutProgramId: Long): ResponseEntity<Response<*>> =
        when (val result = this.boughtProgramService.buy(workoutProgramId)) {
            is Success -> ResponseEntity.ok(Success(result.result))
            is BadRequest -> ResponseEntity.badRequest().body(BadRequest(result.result))
            is NotFound -> ResponseEntity(NotFound(result.result), HttpStatus.NOT_FOUND)
        }

}
