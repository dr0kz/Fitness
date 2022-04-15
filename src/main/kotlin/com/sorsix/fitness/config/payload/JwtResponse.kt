package com.sorsix.fitness.config.payload

class JwtResponse(
    val token: String,
    val id: Long,
    val email: String,
    val name: String,
    val surname: String,
    val description: String,
    val roles: List<String>,
) {
    var tokenType = "Bearer"
}