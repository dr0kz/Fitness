package com.sorsix.fitness.api;

import com.sorsix.fitness.service.DayService
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/day")
class DayController(dayService : DayService) {
}
