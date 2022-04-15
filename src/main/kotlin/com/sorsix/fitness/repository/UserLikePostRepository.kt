package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.UserLikePost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserLikePostRepository : JpaRepository<UserLikePost, Long> {
    fun existsByUserIdAndPostId(userId: Long, postId: Long): Boolean
    fun deleteUserLikePostByUserIdAndPostId(userId: Long, postId: Long)

}