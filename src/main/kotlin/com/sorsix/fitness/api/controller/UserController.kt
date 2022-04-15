package com.sorsix.fitness.api.controller

import com.sorsix.fitness.api.dto.EditProfileRequest
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @PutMapping("/follow")
    fun followUser(@RequestParam userFollowingId: Long, @RequestParam userFollowerId: Long) =
        userService.followUser(userFollowingId,userFollowerId)

    @PutMapping("/muscle")
    fun musclePost(@RequestParam userId: Long, @RequestParam postId: Long) =
        userService.musclePost(userId,postId)

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