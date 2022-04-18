package com.sorsix.fitness.api.controller

import com.sorsix.fitness.api.dto.*
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @PutMapping("/follow")
    fun followUser(
        @RequestParam userFollowingId: Long,
        @RequestParam userFollowerId: Long
    ): ResponseEntity<Response<*>> =
        when (val result = userService.followUser(userFollowingId,userFollowerId)) {
            is Success -> ResponseEntity.ok(Success(result.result))
            is BadRequest -> ResponseEntity.badRequest().body(BadRequest(result.result))
            is NotFound -> ResponseEntity(NotFound(result.result), HttpStatus.NOT_FOUND)
        }

    @PutMapping("/muscle/{postId}")
    fun musclePost(@PathVariable postId: Long) =
        when (val result = userService.musclePost(postId)) {
            is Success -> ResponseEntity.ok(Success(result.result))
            is BadRequest -> ResponseEntity.badRequest().body(BadRequest(result.result))
            is NotFound -> ResponseEntity(NotFound(result.result), HttpStatus.NOT_FOUND)
        }


    @PutMapping("/edit-profile/{id}")
    fun editProfile(@PathVariable id: Long, @RequestBody editReq: EditProfileRequest) =
        with(editReq) {
            userService.updateProfile(id, email, name, surname, password, confirmPassword, description)
        }

    @GetMapping("/search")
    fun searchUsers(@RequestParam search: String?): List<User> {
        return userService.getSearchedUsers(search)
    }
}