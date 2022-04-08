package com.sorsix.fitness.domain.entities

import com.sorsix.fitness.domain.enum.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name="users")
data class User (
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @CollectionTable(
//        name="user_roles",
//        joinColumns = [JoinColumn(name = "user_id")],
//    )
//    private var roles : Set<UserRole>  = HashSet<>()

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
}
