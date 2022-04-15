package com.sorsix.fitness.api.controller

import com.sorsix.fitness.api.dto.PostCreateRequest
import com.sorsix.fitness.api.dto.PostUpdateRequest
import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.service.PostService
import com.sorsix.fitness.service.UserLikePostService
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

@RestController
@RequestMapping("/api/posts")
class PostController(val postService: PostService,
                     val userLikePostService: UserLikePostService) {

    @GetMapping
    fun listAllPostsByPage(@RequestParam page: Int, @RequestParam pageSize: Int): List<Post> =
        this.postService.listAllByPage(page, pageSize).toList()

    @GetMapping("/{id}")
    fun findAllByUserId(@PathVariable id: Long): List<Post> =
        this.postService.findAllByUserId(id)

    @PostMapping("/add")
    fun createPost(@RequestBody postRequest: PostCreateRequest): ResponseEntity<Post> =
        with(postRequest) {
            val post = postService.createPost(description, image, userId)
            ResponseEntity.ok(post)
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

    @PutMapping("/muscle")
    fun musclePost(@RequestParam userId: Long,
                   @RequestParam postId: Long) = userLikePostService.musclePost(userId,postId)
}