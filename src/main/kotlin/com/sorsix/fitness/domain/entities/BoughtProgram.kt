package com.sorsix.fitness.domain.entities

import javax.persistence.*

@Entity
@Table(name = "bought_programs")
data class BoughtProgram(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = User(),

    @ManyToOne
    @JoinColumn(name = "workout_program_id")
    val workoutProgram: WorkoutProgram = WorkoutProgram()
)
