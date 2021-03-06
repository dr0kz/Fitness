package com.sorsix.fitness.domain.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val muscles: Int = 0,

    @Column(nullable = false)
    val dateCreated: LocalDateTime = LocalDateTime.now(),

    val description: String = "",

    @Lob
    @Column(nullable = false)
    val image: ByteArray = ByteArray(1),

    @ManyToOne
    val user: User = User(),

    @Transient
    val likedBy: Boolean = false,
)
