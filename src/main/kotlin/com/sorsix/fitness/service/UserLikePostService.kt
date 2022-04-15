package com.sorsix.fitness.service

import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.UserLikePost
import com.sorsix.fitness.repository.PostRepository
import com.sorsix.fitness.repository.UserLikePostRepository
import com.sorsix.fitness.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserLikePostService(val userLikePostRepository: UserLikePostRepository,
                          val postRepository: PostRepository,
                          val postService: PostService,
                          val userRepository: UserRepository) {

    @Transactional
    fun musclePost(userId: Long, postId: Long): Boolean {
        return if(userLikePostRepository.existsByUserIdAndPostId(userId,postId)){
            //znaci deka toj user vekje go lajknal toj post i treba dislike da napravi odnosno -1 broj na lajkovi
            postRepository.dislike(postId)
            userLikePostRepository.deleteUserLikePostByUserIdAndPostId(userId,postId)
            false
        } else {
            //znaci deka toj user ne go lajknal toj post i treba like odnosno +1 broj na lajkovi da se napravi
            postRepository.like(postId)
            val user: Optional<User> = userRepository.findById(userId)
            val post: Optional<Post> = postRepository.findById(postId)
            userLikePostRepository.save(UserLikePost(0L,user.get(),post.get()))
            true
        }
    }

}