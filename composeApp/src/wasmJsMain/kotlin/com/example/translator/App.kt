package com.example.translator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource

import translatorapp.composeapp.generated.resources.Res
import translatorapp.composeapp.generated.resources.compose_multiplatform

@Composable
fun Header(onHistoryClick: () -> Unit, onAboutClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Переводчик", style = MaterialTheme.typography.h4)

        Row {
            // История
            Button(onClick = onHistoryClick) {
                Text("История")
            }

            // О нас
            Button(onClick = onAboutClick) {
                Text("О нас")
            }
        }
    }
}

@Composable
fun AboutUsDialog(isVisible: Boolean, onDismiss: () -> Unit) {
    if (isVisible) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = 24.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("О нас", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Команда переводчика:", style = MaterialTheme.typography.body1)
                    Text("Контактная информация: team@example.com", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onDismiss) {
                        Text("Закрыть")
                    }
                }
            }
        }
    }
}

@Composable
fun TranslationForm(onTranslate: (String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var faculty by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = faculty,
            onValueChange = { faculty = it },
            label = { Text("Факультет") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(onClick = {
            // Здесь будет логика перевода
            result = "Переведенное значение: ${name} - ${faculty}"
            onTranslate(name, faculty)  // Вызов переводной функции
        }) {
            Text("Перевести")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Результат перевода: $result")
    }
}

@Composable
fun HistoryPage(history: List<String>) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("История переводов:", style = MaterialTheme.typography.h5)
        history.forEach { translation ->
            Text(translation, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun App() {
    var isAboutVisible by remember { mutableStateOf(false) }
    var history by remember { mutableStateOf(listOf<String>()) }

    var currentPage by remember { mutableStateOf("main") }
    var name by remember { mutableStateOf("") }
    var faculty by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Шапка
        Header(
            onHistoryClick = { currentPage = "history" },
            onAboutClick = { isAboutVisible = true }
        )

        // Отображение страницы
        when (currentPage) {
            "main" -> {
                TranslationForm(
                    onTranslate = { n, f ->
                        name = n
                        faculty = f
                        // Добавляем перевод в историю
                        history = history + "Перевод: $name - $faculty"
                    }
                )
            }
            "history" -> {
                HistoryPage(history)
            }
        }

        // Диалог "О нас"
        AboutUsDialog(isVisible = isAboutVisible, onDismiss = { isAboutVisible = false })
    }
}
