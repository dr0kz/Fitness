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
    val user: User = User()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (muscles != other.muscles) return false
        if (dateCreated != other.dateCreated) return false
        if (description != other.description) return false
        if (!image.contentEquals(other.image)) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + muscles
        result = 31 * result + dateCreated.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + user.hashCode()
        return result
    }
}
