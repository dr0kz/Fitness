package com.sorsix.fitness.domain.enum

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    TRAINEE,
    TRAINER;

    override fun getAuthority(): String = name
}