package com.example.walkertracker.data.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp // Используем мощный движок OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {
    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Важно! Если .NET пришлет больше полей, чем у нас в DTO, приложение не упадет.
                encodeDefaults = true
            })
        }
        // Здесь позже добавим перехватчик для вставки JWT-токена во все запросы
    }
}