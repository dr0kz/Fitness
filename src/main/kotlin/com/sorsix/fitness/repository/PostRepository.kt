package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post,Long> {

    override fun findAll(pageable: Pageable): Page<Post>

    fun findAllByUserId(id: Long): List<Post>
}