package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.service.DayService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/day")
class DayController(val dayService : DayService) {

    //create
    @PostMapping("/create")
    fun createDay() {

    }

    //read

    //update


}
