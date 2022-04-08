package com.sorsix.fitness.domain.enum

enum class DayOfWeek(val value: Int) {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}