package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User,Long> {

}