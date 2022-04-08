package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.BoughtProgram
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoughtProgramRepository : JpaRepository<BoughtProgram,Long> {

}