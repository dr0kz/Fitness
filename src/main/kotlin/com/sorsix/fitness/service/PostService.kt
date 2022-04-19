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
import java.time.LocalDateTime
import javax.transaction.Transactional


@Service
class PostService(
    val postRepository: PostRepository,
    val userLikePostRepository: UserLikePostRepository,
    val userRepository: UserRepository
) {

    fun listAllByPage(page: Int, pageSize: Int, date: LocalDateTime): List<Post> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return postRepository.findAllByDateCreatedBeforeOrderByDateCreatedDesc(PageRequest.of(page, pageSize), date)
            .stream()
            .map { t -> t.copy(likedBy = userLikePostRepository.existsByUserIdAndPostId(user.id, t.id)) }
            .toList();
    }

    fun findAllByUser(): List<Post> {
        val user: User = SecurityContextHolder.getContext().authentication.principal as User
        return this.postRepository.findAllByUserId(user.id)
            .stream()
            .map { t -> t.copy(likedBy = userLikePostRepository.existsByUserIdAndPostId(user.id, t.id)) }
            .toList()
    }

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