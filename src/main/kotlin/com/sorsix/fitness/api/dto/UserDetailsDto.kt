package com.sorsix.fitness.api.dto

import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.enum.Role

data class UserDetailsDto(
    var username: String = "",
    var role: Role = Role.TRAINEE) {

    companion object {
        fun of(user: User) : UserDetailsDto {
            val details = UserDetailsDto()
            details.username = user.username
            details.role = user.role
            return details
        }
    }
}