package com.sorsix.fitness.service

import com.sorsix.fitness.api.dto.BadRequest
import com.sorsix.fitness.api.dto.NotFound
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.config.PasswordEncoder
import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.UserFollowUser
import com.sorsix.fitness.domain.entities.UserLikePost
import com.sorsix.fitness.repository.PostRepository
import com.sorsix.fitness.repository.UserFollowUserRepository
import com.sorsix.fitness.repository.UserLikePostRepository
import com.sorsix.fitness.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserService(
    val userRepository: UserRepository,
    val userFollowUserRepository: UserFollowUserRepository,
    val userLikePostRepository: UserLikePostRepository,
    val postRepository: PostRepository,
    val postService: PostService,
    val encoder: PasswordEncoder) {

    @Transactional
    fun followUser(followingId: Long, followerId: Long): Boolean {
        val userFollowing: Optional<User> = userRepository.findById(followingId)
        val userFollower: Optional<User> = userRepository.findById(followerId)

        return if(userFollowUserRepository.existsByUserFollowingIdAndUserFollowerId(followingId,followerId)){
            userFollowUserRepository.deleteUserFollowUserByUserFollowerIdAndUserFollowingId(followerId,followingId)
            userRepository.updateNumberOfFollowersMinus(followerId)
            userRepository.updateNumberOfFollowingMinus(followingId)
            userRepository.save(userFollower.get())
            userRepository.save(userFollowing.get())
            false
        } else {
            userFollowUserRepository.save(UserFollowUser(0L,userFollowing.get(),userFollower.get()))
            userRepository.updateNumberOfFollowersPlus(followerId)
            userRepository.updateNumberOfFollowingPlus(followingId)
            userRepository.save(userFollower.get())
            userRepository.save(userFollowing.get())
            true
        }
    }

    @Transactional
    fun musclePost(userId: Long, postId: Long): Boolean {
        return if(userLikePostRepository.existsByUserIdAndPostId(userId,postId)){
            postRepository.dislike(postId)
            userLikePostRepository.deleteUserLikePostByUserIdAndPostId(userId,postId)
            false
        } else {
            postRepository.like(postId)
            val user: Optional<User> = userRepository.findById(userId)
            val post: Optional<Post> = postRepository.findById(postId)
            userLikePostRepository.save(UserLikePost(0L,user.get(),post.get()))
            true
        }
    }

    @Transactional
    fun updateProfile(id: Long, email: String?, name: String?, surname: String?,
                      password: String?, confirmPassword: String?, description: String?): Response<*> {
        val user = userRepository.findById(id)
        if(user.isPresent) {
            if (email!=null && email.contains("@") && email.contains(".com")) {
                if (this.userRepository.findAll().stream().noneMatch { u -> u.id != id && u.email == email }) {
                    userRepository.updateEmail(id, email)
                } else {
                    return BadRequest("Email $email is already taken")
                }
            } else {
                return BadRequest("Email $email not valid")
            }

            if(name!=null) userRepository.updateName(id,name)

            if(surname!=null) userRepository.updateSurname(id,surname)

            if(description!=null) userRepository.updateDescription(id,description)

            if(password != null && confirmPassword != null && password == confirmPassword) {
                val newPassword = encoder.passwordEncoderBean().encode(password)
                userRepository.updatePassword(id,newPassword)
            }

            userRepository.save(user.get())
            return Success("Successfully updated profile for user with id $id")
        } else {
            return NotFound("User with id $id does not exist")
        }
    }

    fun getSearchedUsers(search: String?): List<User> {
        return if (search!=null)
            userRepository.findAllByNameLikeOrSurnameLikeOrderByFollowersNum(search,search)
        else
            userRepository.findAll()
    }

}