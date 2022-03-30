package com.sorsix.fitness.fitness.domain.entities

import com.sorsix.fitness.fitness.domain.enum.Role
import javax.persistence.*

@Entity
@Table(name="users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val surname: String = "",

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    val password: String = "",

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.TRAINEE,

    val image: String = "",

    val description: String = "",

    )
