package com.sorsix.fitness.service

import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.repository.PostRepository
import com.sorsix.fitness.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostService(val postRepository: PostRepository, val userRepository: UserRepository) {

    fun listAllByPage(page: Int, pageSize: Int) = this.postRepository.findAll(PageRequest.of(page, pageSize))

    fun findAllByUserId(id: Long) = this.postRepository.findAllByUserId(id)

    fun createPost(description: String, image: String, userId: Long): Post {
        val user = this.userRepository.findById(userId).get()
        val post = Post(description = description, image = image, user = user)
        return this.postRepository.save(post)
    }
}