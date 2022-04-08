package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role,Long> {
    fun findByName(name: ERole?): Optional<Role?>
}