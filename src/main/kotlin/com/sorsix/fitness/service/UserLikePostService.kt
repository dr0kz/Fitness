package com.sorsix.fitness.service

import com.sorsix.fitness.api.dto.BadRequest
import com.sorsix.fitness.api.dto.NotFound
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.UserLikePost
import com.sorsix.fitness.repository.PostRepository
import com.sorsix.fitness.repository.UserLikePostRepository
import com.sorsix.fitness.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserLikePostService(
    val userLikePostRepository: UserLikePostRepository,
    val postRepository: PostRepository,
    val postService: PostService,
    val userRepository: UserRepository
) {

    @Transactional
    fun musclePost(postId: Long): Response<*> {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val post: Optional<Post> = this.postRepository.findById(postId)

        if (post.isEmpty) {
            return NotFound("Post with id $postId was not found")
        }

        if (userLikePostRepository.existsByUserIdAndPostId(user.id, postId)) {
            postRepository.dislike(postId)
            userLikePostRepository.deleteUserLikePostByUserIdAndPostId(user.id, postId)
            return Success("Post disliked successfully")
        } else {
            postRepository.like(postId)
            userLikePostRepository.save(UserLikePost(0L, user, post.get()))
            return Success("Post liked successfully")
        }
    }

}