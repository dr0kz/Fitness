package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.service.WeekService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/week")
class WeekController(val weekService : WeekService) {

    @PostMapping("{}")
    fun createWeek(@PathVariable programId: Long, ){

    }
}
