package com.sorsix.fitness.domain.entities

import javax.persistence.*

@Entity
@Table(name = "weeks")
data class Week(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    val workoutProgram: WorkoutProgram = WorkoutProgram()

)
