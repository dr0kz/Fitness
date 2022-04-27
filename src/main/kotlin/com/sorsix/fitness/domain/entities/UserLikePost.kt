package com.sorsix.fitness.domain.entities

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "user_like_posts")
data class UserLikePost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val id: Long = 0L,

    @ManyToOne//(cascade= [CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.DETACH])
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User = User(),

    @ManyToOne//(cascade= [CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.DETACH])
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val post: Post = Post(),

)
