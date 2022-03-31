package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.Week
import org.springframework.data.jpa.repository.JpaRepository

interface WeekRepository : JpaRepository<Week,Long> {

}