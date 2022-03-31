package com.sorsix.fitness.service

import com.sorsix.fitness.repository.DayRepository
import org.springframework.stereotype.Service

@Service
class DayService(val dayRepository : DayRepository) {
}