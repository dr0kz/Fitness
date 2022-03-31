package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.BoughtProgram
import org.springframework.data.jpa.repository.JpaRepository

interface BoughtProgramRepository : JpaRepository<BoughtProgram,Long> {

}