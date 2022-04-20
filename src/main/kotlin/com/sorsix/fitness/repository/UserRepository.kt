package com.sorsix.fitness.repository

import com.sorsix.fitness.api.dto.projection.UserProjection
import com.sorsix.fitness.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>

    fun existsByEmail(email: String): Boolean?

    @Transactional
    @Query("select u.id as id, u.name as name, u.surname as surname, u.image as image from User u" +
            " where concat(lower(u.name),lower(u.surname)) like :searchText")
    fun findAllBySearchText(searchText: String): List<UserProjection>

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

    @Modifying
    @Transactional
    @Query("update User u set u.email = :email where u.id = :userId")
    fun updateEmail(userId: Long, email: String)

    @Modifying
    @Transactional
    @Query("update User u set u.name = :name where u.id = :userId")
    fun updateName(userId: Long, name: String)

    @Modifying
    @Transactional
    @Query("update User u set u.surname = :surname where u.id = :userId")
    fun updateSurname(userId: Long, surname: String)

    @Modifying
    @Transactional
    @Query("update User u set u.description = :description where u.id = :userId")
    fun updateDescription(userId: Long, description: String)

    @Modifying
    @Transactional
    @Query("update User u set u.password = :password where u.id = :userId")
    fun updatePassword(userId: Long, password: String)

    @Modifying
    @Transactional
    @Query("update User u set u.image = :byteArr where u.id = :userId")
    fun updateImage(userId: Long, byteArr: ByteArray)

}