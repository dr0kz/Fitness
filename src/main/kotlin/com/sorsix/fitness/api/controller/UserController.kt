package com.sorsix.fitness.api.controller

import com.sorsix.fitness.api.dto.*
import com.sorsix.fitness.api.dto.projection.UserProjection
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @PutMapping("/follow")
    fun followUser(
        @RequestParam userFollowingId: Long,
        @RequestParam userFollowerId: Long
    ): ResponseEntity<Response<*>> =
        when (val result = userService.followUser(userFollowingId, userFollowerId)) {
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

    @PutMapping("/edit-profile")
    fun editProfile(@RequestParam email: String?,
                    @RequestParam name: String?,
                    @RequestParam surname: String?,
                    @RequestParam password: String?,
                    @RequestParam confirmPassword: String?,
                    @RequestParam description: String?,
                    @RequestParam image: MultipartFile?): ResponseEntity<*> {
        val response = userService.updateProfile(email,name,surname,password,confirmPassword,description,image)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/find-all-by-search-text")
    fun findAllByNameAndSurname(@RequestParam searchText: String): List<UserProjection> {
        return userService.findAllBySearchText(searchText)
    }

}