package com.example.translator

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource

val AccentColor = Color(62,180,137)

data class TranslationHistoryItem(
    val name: String,
    val faculty: String,
    val translation: String
)

@Composable
fun Header(onHistoryClick: () -> Unit, onAboutClick: () -> Unit) {
    MaterialTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Переводчик", style = MaterialTheme.typography.h4)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onHistoryClick,
                    colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
                ) {
                    Text("История", color = Color.White)
                }

                Button(
                    onClick = onAboutClick,
                    colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
                ) {
                    Text("О нас", color = Color.White)
                }

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
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("О нас", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Команда переводчика:", style = MaterialTheme.typography.body1)
                    Text("Бобровников Никита", style = MaterialTheme.typography.body1)
                    Text("Евстигнеев Александр", style = MaterialTheme.typography.body1)
                    Text("Зайниев Владислав", style = MaterialTheme.typography.body1)
                    Text("Ишкова Анна", style = MaterialTheme.typography.body1)
                    Text("Микрюкова Анастасия", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Контактная информация: a.mikryukova119@mail.ru", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
                    ) {
                        Text("Закрыть", color = Color.White)
                    }
                }
            }
        }
    }
}


@Composable
fun TranslationForm(
    onTranslate: (String, String) -> Unit,
    result: String,
    initialName: String = "",
    initialFaculty: String = ""
) {
    var name by remember { mutableStateOf(initialName) }
    var faculty by remember { mutableStateOf(initialFaculty) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(2f)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Первое текстовое поле (Название)
                OutlinedTextField(
                    value = name,
                    onValueChange = { newName ->
                        if (newName.length <= 1000) {
                            name = newName
                        }
                    },
                    maxLines = 7,
                    label = { Text("Название") },
                    modifier = Modifier
                        .weight(1f)
                )

                OutlinedTextField(
                    value = faculty,
                    onValueChange = { newFaculty ->
                        if (newFaculty.length <= 1000) {
                            faculty = newFaculty
                        }
                    },
                    maxLines = 7,
                    label = { Text("Факультет") },
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Button(
                onClick = { onTranslate(name, faculty) },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
            ) {
                Text("Перевести", color = Color.White)
            }

            Text(
                text = "Результат перевода: $result",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(top = 16.dp)
            )
        }
    }
}


@Composable
fun HistoryPage(
    history: List<TranslationHistoryItem>,
    onItemClick: (TranslationHistoryItem) -> Unit,
    onBackClick: () -> Unit // Новый параметр для обработки нажатия на кнопку "Назад"
) {
    MaterialTheme {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // Кнопка "Назад"
            Button(
                onClick = onBackClick,
                modifier = Modifier.padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
            ) {
                Text("Назад", color = Color.White)
            }

            Text("История переводов:", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))

            history.forEach { item ->
                Button(
                    onClick = { onItemClick(item) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Прозрачный фон
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Название: ${item.name}", style = MaterialTheme.typography.body1)
                            Text("Факультет: ${item.faculty}", style = MaterialTheme.typography.body2)
                        }
                        Text("Перевод: ${item.translation}", style = MaterialTheme.typography.body2)
                    }
                }

            }
        }
    }
}


@Composable
fun App() {
    var isAboutVisible by remember { mutableStateOf(false) }
    var isHistoryVisible by remember { mutableStateOf(false) }
    var history by remember { mutableStateOf(listOf<TranslationHistoryItem>()) }
    var translationResult by remember { mutableStateOf("") }
    var initialName by remember { mutableStateOf("") }
    var initialFaculty by remember { mutableStateOf("") }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Header(
                onHistoryClick = { isHistoryVisible = true },
                onAboutClick = { isAboutVisible = true }
            )

            if (isHistoryVisible) {
                HistoryPage(
                    history = history,
                    onItemClick = { item ->
                        initialName = item.name
                        initialFaculty = item.faculty
                        translationResult = item.translation
                        isHistoryVisible = false
                    },
                    onBackClick = { isHistoryVisible = false } // Возвращение на главную страницу
                )
            } else if (isAboutVisible) {
                AboutUsDialog(
                    isVisible = isAboutVisible,
                    onDismiss = { isAboutVisible = false }
                )
            } else {
                TranslationForm(
                    onTranslate = { name, faculty ->
                        val result = "Переведенное значение: $name - $faculty"
                        translationResult = result
                        history = history + TranslationHistoryItem(name, faculty, result)
                    },
                    result = translationResult,
                    initialName = initialName,
                    initialFaculty = initialFaculty
                )
            }
        }
    }
}

