package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.service.BoughtProgramService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bought-programs")
class BoughtProgramController(val boughtProgramService : BoughtProgramService) {

    @GetMapping("/list/{userId}")
    fun listAllByUserId(@PathVariable userId: Long) = this
}
