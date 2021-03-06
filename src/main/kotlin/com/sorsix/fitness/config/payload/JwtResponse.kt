package com.sorsix.fitness.config.payload

class JwtResponse(
    val token: String,
    val id: Long,
    val email: String,
    val name: String,
    val surname: String,
    val description: String,
    val image: ByteArray?,
    val role: String,
    val followingNum: Int,
    val followersNum: Int,
) {
    var tokenType = "Bearer"
}