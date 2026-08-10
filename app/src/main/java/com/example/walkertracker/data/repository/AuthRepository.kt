package com.example.walkertracker.data.repository

import com.example.walkertracker.data.models.AuthResponse
import com.example.walkertracker.data.models.RegisterRequest
import com.example.walkertracker.data.network.ApiClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.contentType

class AuthRepository {
    private val baseUrl = "https://walkingtrackerbackend-production.up.railway.app/api/auth"

    suspend fun register(email: String, password: String): Result<AuthResponse> {
        return try {
            val response: HttpResponse = ApiClient.client.post("$baseUrl/register") {
                contentType(io.ktor.http.ContentType.Application.Json)
                setBody(RegisterRequest(email, password))
            }

            if (response.status.value in 200..299) {
                // Успех: парсим тело ответа в наш data-класс
                Result.success(response.body())
            } else {
                // Ошибка сервера (400, 500...)
                Result.failure(Exception("Server error: ${response.status}"))
            }
        } catch (e: Exception) {
            // Ошибка сети (нет интернета, таймаут)
            Result.failure(e)
        }
    }
}