package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.UserFollowUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserFollowUserRepository : JpaRepository<UserFollowUser,Long> {
    fun existsByUserFollowingIdAndUserFollowerId(userFollowingId: Long, userFollowerId: Long): Boolean
    fun deleteUserFollowUserByUserFollowerIdAndUserFollowingId(userFollowingId: Long, userFollowerId: Long)
}