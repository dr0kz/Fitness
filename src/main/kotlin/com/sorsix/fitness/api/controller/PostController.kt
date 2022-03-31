package com.sorsix.fitness.api.controller;

import com.sorsix.fitness.api.dto.PostRequest
import com.sorsix.fitness.domain.entities.Post
import com.sorsix.fitness.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
class PostController(val postService: PostService) {

    @GetMapping
    fun listAllPostsByPage(@RequestParam page: Int, @RequestParam pageSize: Int): List<Post> =
        this.postService.listAllByPage(page, pageSize).toList()

    @GetMapping("/{id}")
    fun findAllByUserId(@PathVariable id: Long): List<Post> =
        this.postService.findAllByUserId(id)

    @PostMapping("/add")
    fun createPost(@RequestBody postRequest: PostRequest): ResponseEntity<Post> =
        with(postRequest) {
            val post = postService.createPost(description, image, userId)
            ResponseEntity.ok(post)
        }

}
