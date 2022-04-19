package com.sorsix.fitness.api.controller

import com.sorsix.fitness.api.dto.PostUpdateRequest
import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.service.PostService
import com.sorsix.fitness.service.UserService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/posts")
class PostController(
    val postService: PostService,
    val userService: UserService,
) {

    @GetMapping
    fun listAllPostsByPage(
        @RequestParam page: Int,
        @RequestParam pageSize: Int,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") firstGetRequestDateTime: LocalDateTime
    ): List<Post> =
        this.postService.listAllByPage(page, pageSize, firstGetRequestDateTime)

    @GetMapping("/find-all-by-user")
    fun findAllByUser(): List<Post> {
        return this.postService.findAllByUser()
    }

    @GetMapping("/find-all-by-user/{id}")
    fun findAllByUserId(@PathVariable id: Long): List<Post> {
        return this.postService.findAllByUserId(id)
    }

    @PostMapping("/add")
    fun createPost(@RequestParam description: String, @RequestParam image: MultipartFile): ResponseEntity<Post> {
        val post = postService.createPost(description, image)
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("/delete/{id}")
    fun deletePost(@PathVariable id: Long) = this.postService.deletePost(id)

    @PutMapping("/update/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody postRequest: PostUpdateRequest) =
        with(postRequest) {
            postService.updatePost(id, description, image)
        }

    @PutMapping("/like/{id}")
    fun likePost(@PathVariable id: Long) = postService.likePost(id)

    @PutMapping("/dislike/{id}")
    fun dislikePost(@PathVariable id: Long) = postService.dislikePost(id)

    @PutMapping("/likeOrDislike/{postId}")
    fun musclePost(@PathVariable postId: Long) = userService.musclePost(postId)
}