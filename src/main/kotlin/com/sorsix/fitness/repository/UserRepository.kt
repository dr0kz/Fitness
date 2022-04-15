package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface UserRepository : JpaRepository<User,Long> {
    fun findByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean?

    @Modifying
    @Transactional
    @Query("update User u set u.followersNum = u.followersNum+1 where u.id = :userId")
    fun updateNumberOfFollowersPlus(userId: Long)

    @Modifying
    @Transactional
    @Query("update User u set u.followingNum = u.followingNum+1 where u.id = :userId")
    fun updateNumberOfFollowingPlus(userId: Long)

    @Modifying
    @Transactional
    @Query("update User u set u.followingNum = u.followingNum-1 where u.id = :userId")
    fun updateNumberOfFollowingMinus(userId: Long)

    @Modifying
    @Transactional
    @Query("update User u set u.followersNum = u.followersNum-1 where u.id = :userId")
    fun updateNumberOfFollowersMinus(userId: Long)

}