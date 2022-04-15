package com.sorsix.fitness.service

import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.repository.PostRepository
import com.sorsix.fitness.repository.UserLikePostRepository
import com.sorsix.fitness.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


@Service
class PostService(
    val postRepository: PostRepository,
    val userLikePostRepository: UserLikePostRepository,
    val userRepository: UserRepository
) {

    fun listAllByPage(page: Int, pageSize: Int) = this.postRepository.findAll(PageRequest.of(page, pageSize))

    fun findAllByUserId(id: Long) = this.postRepository.findAllByUserId(id)

    fun createPost(description: String, image: MultipartFile): Post {
        val user = SecurityContextHolder.getContext().authentication.principal as User


        val byteArr: ByteArray = image.bytes
        ByteArrayInputStream(byteArr)

        val post = Post(description = description, image = byteArr, user = user)
        return this.postRepository.save(post)
    }

    fun updatePost(id: Long, description: String, image: String) {
        val post = this.postRepository.findById(id)
        if (post.isPresent) {
            this.postRepository.update(id, description, image)
        }
    }

    fun likePost(id: Long) {
        val post = this.postRepository.findById(id)
        if (post.isPresent) {
//            val userLikePost = UserLikePost()
//            userLikePostRepository.save()
            this.postRepository.like(id)
        }
    }

    fun dislikePost(id: Long) {
        val post = this.postRepository.findById(id)
        if (post.isPresent) {
            this.postRepository.dislike(id)
        }
    }

    fun deletePost(id: Long) {
        val post = this.postRepository.findById(id)
        if (post.isPresent) {
            this.postRepository.delete(post.get())
        }
    }
}