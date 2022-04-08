package com.sorsix.fitness.api.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/test")
class TestController {
    @GetMapping("/all")
    fun allAccess(): String {
        return "Public Content."
    }

    @GetMapping("/trainee")
    @PreAuthorize("hasRole('TRAINEE')")
    fun userAccess(): String {
        return "User TRAINEE."
    }

    @GetMapping("/trainer")
    @PreAuthorize("hasRole('TRAINER')")
    fun moderatorAccess(): String {
        return "User TRAINER."
    }

}