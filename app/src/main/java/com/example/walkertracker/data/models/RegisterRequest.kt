package com.example.walkertracker.data.models

import kotlinx.serialization.Serializable

@Serializable // Эта пометка позволит Ktor превратить объект в строку "{\"email\":\"...\"}"
data class RegisterRequest(
    val email: String,
    val password: String
)