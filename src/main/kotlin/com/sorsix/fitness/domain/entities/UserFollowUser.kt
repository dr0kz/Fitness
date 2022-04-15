package com.sorsix.fitness.domain.entities

import javax.persistence.*

@Entity
@Table(name = "user_follow_user")
data class UserFollowUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name="user_following_id")
    val userFollowing: User = User(),

    @ManyToOne
    @JoinColumn(name="user_follower_id")
    val userFollower: User = User(),

)