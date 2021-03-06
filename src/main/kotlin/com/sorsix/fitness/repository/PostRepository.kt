package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.transaction.Transactional

@Repository
interface PostRepository : JpaRepository<Post,Long> {

    @Transactional
    fun findAllByDateCreatedBeforeOrderByDateCreatedDesc(pageable: Pageable, date: LocalDateTime): Page<Post>

    @Transactional
    fun findAllByUserId(id: Long): List<Post>

    @Modifying
    @Transactional
    @Query("update Post p set p.description = :description, p.image = :image where p.id = :id")
    fun update(id: Long, description: String, image: String)

    @Modifying
    @Transactional
    @Query("update Post p set p.muscles = p.muscles+1 where p.id = :id")
    fun like(id: Long)

    @Modifying
    @Transactional
    @Query("update Post p set p.muscles = p.muscles-1 where p.id = :id")
    fun dislike(id: Long)

}