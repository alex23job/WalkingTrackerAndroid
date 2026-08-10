package com.example.walkertracker.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)