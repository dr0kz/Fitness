package com.sorsix.fitness.service

import com.sorsix.fitness.api.dto.*
import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.enum.Role
import com.sorsix.fitness.domain.exceptions.InvalidArgumentsException
import com.sorsix.fitness.domain.exceptions.InvalidUserEmailException
import com.sorsix.fitness.domain.exceptions.PasswordsDoNotMatchException
import com.sorsix.fitness.domain.exceptions.UsernameAlreadyExistsException
import com.sorsix.fitness.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) {

    private fun isNullOrEmpty(value: String?): Boolean {
        return value == null || value.isEmpty()
    }

    fun register(
        email: String,
        firstName: String,
        lastName: String,
        role: Role,
        password: String,
        confirmPassword: String
    ): Response<*> {

        if (password != confirmPassword) {
            return BadRequest("Passwords do not match")
        }
        if (userRepository.findByEmail(email).isPresent) {
            return BadRequest("Email $email already exists")        }
        val user = User(name = firstName, surname = lastName, email = email, password = password, role = role)

        return Success(userRepository.save(user))
    }

    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email).orElseThrow { InvalidUserEmailException(email) }
    }

    @Throws(UsernameNotFoundException::class)
    fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(email).orElseThrow { UsernameNotFoundException(email) }
    }
}