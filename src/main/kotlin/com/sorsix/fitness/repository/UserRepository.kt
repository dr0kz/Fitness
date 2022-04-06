package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User,Long> {

    fun findByEmail(email: String): Optional<User>
}