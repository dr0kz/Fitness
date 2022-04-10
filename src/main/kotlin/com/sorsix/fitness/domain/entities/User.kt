package com.sorsix.fitness.domain.entities

import com.sorsix.fitness.domain.enum.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name="users")
class User (
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
    private val password: String = "",

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.TRAINEE,

    val image: String = "",

    val description: String = "",


    ) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return mutableListOf(role)
    }

    override fun getPassword(): String = password

    //override fun getUsername(): String = "$name $surname" //da bide email?
    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as User
        return id == user.id
    }

    companion object {
        private const val serialVersionUID = 1L
        fun build(user: User): User {

            val authorities : List<GrantedAuthority> = listOf(user.role)
            return User(
                user.id,
                user.name,
                user.surname,
                user.email,
                user.password,
                user.role,
            )
        }
    }
}
