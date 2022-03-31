package com.sorsix.fitness.api;

import com.sorsix.fitness.service.BoughtProgramService
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bought-program")
class BoughtProgramController(val boughtProgramService : BoughtProgramService) {
}
