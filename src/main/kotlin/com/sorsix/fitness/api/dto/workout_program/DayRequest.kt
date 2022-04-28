package com.sorsix.fitness.api.dto.workout_program

data class DayRequest(
    val title: String = "",
    val description: String = "",
    val video: String = "", ) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DayRequest

        if (title != other.title) return false
        if (description != other.description) return false
        if (video != other.video) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + video.hashCode()
        return result
    }
}
