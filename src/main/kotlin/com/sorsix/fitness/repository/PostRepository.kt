package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface PostRepository : JpaRepository<Post,Long> {

    override fun findAll(pageable: Pageable): Page<Post>

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