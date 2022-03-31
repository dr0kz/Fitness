package com.sorsix.fitness.repository

import com.sorsix.fitness.domain.entities.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post,Long> {

}