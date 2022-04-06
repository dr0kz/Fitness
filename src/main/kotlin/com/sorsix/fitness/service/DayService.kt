package com.sorsix.fitness.service

import com.sorsix.fitness.domain.entities.Day
import com.sorsix.fitness.repository.DayRepository
import org.springframework.stereotype.Service


@Service
class DayService(val dayRepository: DayRepository) {

    fun findAllByWorkoutProgramId(id: Long): Map<Int,List<Day>> =
        this.dayRepository.findAllByWorkoutProgramId(id).groupBy { it.week }

}