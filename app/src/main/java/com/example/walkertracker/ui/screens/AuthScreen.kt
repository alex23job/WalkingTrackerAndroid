package com.example.walkertracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.walkertracker.data.repository.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(modifier: Modifier = Modifier,
               authRepository: AuthRepository = AuthRepository()) {
    // Состояние полей ввода (аналог useState в React)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State для отображения прогресса загрузки
    var isLoading by remember { mutableStateOf(false) }

    // CoroutineScope для запуска сетевых запросов из UI
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Walker Tracker", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                autoCorrect = false
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(), // Скрывает символы
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        /*Button(onClick = {
            // Сюда позже добавим отправку запроса
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Register")
        }*/
        Button(
            onClick = {
                // Вызываем регистрацию через корутину
                scope.launch {
                    isLoading = true // Показываем индикатор

                    // Запускаем сеть (suspend-функция)
                    val result = authRepository.register(email, password)

                    // Проверяем результат
                    if (result.isSuccess) {
                        // Успех!
                        val tokenResponse = result.getOrNull()
                        // TODO: Сохранить токен и перейти на карту
                    } else {
                        // Ошибка
                        val error = result.exceptionOrNull()
                        // TODO: Показать Toast с текстом ошибки
                    }

                    isLoading = false // Скрываем индикатор
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Кнопка "нажимается" только когда нет запроса
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Register")
            }
        }
    }
}