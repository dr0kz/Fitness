package com.sorsix.fitness.config

class JwtAuthConstants {
    companion object {
        const val SECRET = "s3cr3tt0k3n"          //token secret za enkripcija kako potpis
        const val EXPIRATION_TIME = 864000000     //10 days
        const val TOKEN_PREFIX = "Bearer "        //prefix na token
        const val HEADER_STRING = "Authorization" //header kako da glasi
    }
}