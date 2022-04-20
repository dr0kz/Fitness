package com.sorsix.fitness.service

import com.sorsix.fitness.api.dto.BadRequest
import com.sorsix.fitness.api.dto.NotFound
import com.sorsix.fitness.api.dto.Response
import com.sorsix.fitness.api.dto.Success
import com.sorsix.fitness.api.dto.projection.UserProjection
import com.sorsix.fitness.config.PasswordEncoder
import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.UserFollowUser
import com.sorsix.fitness.domain.entities.UserLikePost
import com.sorsix.fitness.repository.PostRepository
import com.sorsix.fitness.repository.UserFollowUserRepository
import com.sorsix.fitness.repository.UserLikePostRepository
import com.sorsix.fitness.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.*
import javax.transaction.Transactional

@Service
class UserService(
    val userRepository: UserRepository,
    val userFollowUserRepository: UserFollowUserRepository,
    val userLikePostRepository: UserLikePostRepository,
    val postRepository: PostRepository,
    val postService: PostService,
    val encoder: PasswordEncoder
) {

    @Transactional
    fun followUnfollowUser(followerId: Long): Response<*> {
        val userFollowing: User = SecurityContextHolder.getContext().authentication.principal as User
        val userFollower: Optional<User> = userRepository.findById(followerId)

        if (userFollower.isEmpty) {
            return NotFound("User with id $followerId was not found")
        }

        return if (userFollowUserRepository.existsByUserFollowingIdAndUserFollowerId(userFollowing.id, followerId)) {
            userFollowUserRepository.deleteUserFollowUserByUserFollowerIdAndUserFollowingId(followerId, userFollowing.id)
            userRepository.updateNumberOfFollowersMinus(followerId)
            userRepository.updateNumberOfFollowingMinus(userFollowing.id)
            userRepository.save(userFollower.get())
            userRepository.save(userFollowing)
            Success("Ok")
        } else {
            userFollowUserRepository.save(UserFollowUser(0L, userFollowing, userFollower.get()))
            userRepository.updateNumberOfFollowersPlus(followerId)
            userRepository.updateNumberOfFollowingPlus(userFollowing.id)
            userRepository.save(userFollower.get())
            userRepository.save(userFollowing)
            Success("Ok")
        }
    }

    @Transactional
    fun musclePost(@PathVariable postId: Long): Response<*> {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val post: Optional<Post> = this.postRepository.findById(postId)

        if (post.isEmpty) {
            return NotFound("Post with id $postId was not found")
        }

        return if (userLikePostRepository.existsByUserIdAndPostId(user.id, postId)) {
            postRepository.dislike(postId)
            userLikePostRepository.deleteUserLikePostByUserIdAndPostId(user.id, postId)
            Success("Post disliked successfully")
        } else {
            postRepository.like(postId)
            userLikePostRepository.save(UserLikePost(0L, user, post.get()))
            Success("Post liked successfully")
        }
    }

    @Transactional
    fun updateProfile(
        email: String?, name: String?, surname: String?,
        password: String?, confirmPassword: String?, description: String?, image: MultipartFile?
    ): Response<*> {
        val user: User = SecurityContextHolder.getContext().authentication.principal as User

        if (email != null && email != "") {
            if (this.userRepository.findAll().stream().noneMatch { u -> u.id != user.id && u.email == email }) {
                userRepository.updateEmail(user.id, email)
            } else {
                return BadRequest("Email $email is already taken")
            }
        }
        if (name != null && name != "") {
            userRepository.updateName(user.id, name)
        }
        if (surname != null && surname != "") {
            userRepository.updateSurname(user.id, surname)
        }
        if (description != null && description != "") {
            userRepository.updateDescription(user.id, description)
        }
        if (password != null && password != "" && confirmPassword != null && confirmPassword != "") {
            if (password == confirmPassword) {
                val newPassword = encoder.passwordEncoderBean().encode(password)
                userRepository.updatePassword(user.id, newPassword)
            } else {
                return BadRequest("Passwords not matching")
            }
        }

        val newImage = if (image != null) {
            val byteArr: ByteArray = image.bytes
            ByteArrayInputStream(byteArr)

            userRepository.updateImage(user.id, byteArr)
            byteArr
        } else {
            user.image
        }

        val newPassword = if (password != null) {
            encoder.passwordEncoderBean().encode(password)
        } else {
            user.password
        }

        val temp = user.copy(
            id = user.id,
            name = name ?: user.name,
            surname = surname ?: user.surname,
            email = email ?: user.email,
            password = newPassword,
            role = user.role,
            image = newImage,
            description = description ?: user.description,
            followersNum = user.followersNum,
            followingNum = user.followingNum
        )

        return Success(temp)
    }

    fun findAllBySearchText(searchText: String): List<UserProjection> {
        val searchTextToLower = searchText.lowercase(Locale.getDefault()).replace(" ", "")
        return userRepository.findAllBySearchText("%$searchTextToLower%")
    }

    fun findUserById(id: Long): User? {
        val user = userRepository.findById(id)
        return if (user.isPresent) {
            user.get()
        } else {
            null
        }
    }

}