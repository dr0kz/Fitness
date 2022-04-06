package com.sorsix.fitness.domain.entities

import javax.persistence.*

@Entity
@Table(name = "user_like_posts")
data class UserLikePost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = User(),

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post = Post(),

)
