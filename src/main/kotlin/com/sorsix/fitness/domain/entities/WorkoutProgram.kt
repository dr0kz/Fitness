package com.sorsix.fitness.domain.entities

import javax.persistence.*

@Entity
@Table(name = "workout_programs")
data class WorkoutProgram(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val price: Int = 0,

    @Column(nullable = false)
    val description: String = "",

    @ManyToOne
    val userTrainer: User = User()

)
