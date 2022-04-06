package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.UserLikePost
import org.springframework.data.jpa.repository.JpaRepository


interface UserLikePostRepository : JpaRepository<UserLikePost, Long> {
}