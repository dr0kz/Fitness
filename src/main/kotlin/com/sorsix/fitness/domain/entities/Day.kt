package com.sorsix.fitness.domain.entities

import com.sorsix.fitness.domain.enum.DayOfWeek
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "days")
data class Day(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,

    @Column(nullable = false)
    val title: String = "",

    @Column(nullable = false)
    val description: String = "",

    val video: String = "",

    val week: Int = 1,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val workoutProgram: WorkoutProgram = WorkoutProgram()
)
