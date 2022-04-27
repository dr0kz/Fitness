package com.sorsix.fitness.domain.entities

import com.sorsix.fitness.domain.enum.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "users")
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
    private val password: String = "",

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.TRAINEE,

    @Lob
    val image: ByteArray? = null,

    val description: String = "",

    val followersNum: Int = 0,

    val followingNum: Int = 0,

    @Transient
    val followedBy: Boolean = false,

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

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + description.hashCode()
        result = 31 * result + followersNum
        result = 31 * result + followingNum
        return result
    }

    companion object {
        private const val serialVersionUID = 1L
        fun build(user: User): User {

            val authorities: List<GrantedAuthority> = listOf(user.role)
            return User(
                user.id,
                user.name,
                user.surname,
                user.email,
                user.password,
                user.role,
                user.image,
                user.description,
                user.followersNum,
                user.followingNum,
            )
        }
    }

}
