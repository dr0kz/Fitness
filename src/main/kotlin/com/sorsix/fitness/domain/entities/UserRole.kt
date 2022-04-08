package com.sorsix.fitness.domain.entities

import com.sorsix.fitness.domain.enum.Role
import javax.persistence.*


@Entity
@Table(name = "roles")
data class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    @ElementCollection
    var name: Role? = null //fix?
) {

    @JvmName("getName1")
    fun getName(): Role? {
        return name
    }

    @JvmName("setName1")
    fun setName(name: Role?) {
        this.name = name
    }

    @JvmName("getId1")
    fun getId(): Long {
        return id
    }

    @JvmName("setId1")
    fun setId(id: Long) {
        this.id = id
    }
}