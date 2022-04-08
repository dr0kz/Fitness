package com.sorsix.fitness.config.payload

data class SignupRequest(
    var email: String? = null, //username

    var role: Set<String>? = null,

    var password: String? = null
) {
    var username = email
}