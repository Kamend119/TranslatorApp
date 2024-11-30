@file:Suppress("UNUSED_EXPRESSION")

package com.example.translator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// Цвета
val AccentColor = Color(62,180,137) // Акцентный цвет
var AdditionalAccent = Color(237,247,244) // Дополнительный акцент

// Максимальная длина для ввода в поля перевода
var MaxLengthText = 1000

// Максимальная длина для логина и паролей
var MaxLengthAut = 100

data class TranslationHistoryItem(
    val name: String,
    val faculty: String,
    val translation: String
)

@Composable
fun App() {
    var currentPage by remember { mutableStateOf("Login") }

    when (currentPage) {
        "Login" -> LoginPage(
            onLoginClick = { currentPage = "Translator" },
            onRegisterClick = { currentPage = "Register" }
        )
        "Register" -> RegisterPage(
            onBackToLoginClick = { currentPage = "Translator" }
        )
        "Translator" -> TranslatorPage(onLogoutClick = { currentPage = "Login" } )
    }
}

@Composable
fun LoginPage(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Переводчик", fontSize = 24.sp, modifier = Modifier.padding(bottom = 32.dp))

            OutlinedTextField(
                value = login,
                onValueChange = { newLogin -> if (newLogin.length <= MaxLengthAut) login = newLogin },
                maxLines = 1,
                label = { Text("Логин") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { newPassword -> if (newPassword.length <= MaxLengthAut) password = newPassword },
                maxLines = 1,
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = onRegisterClick) {
                    Text("Регистрация", color = MaterialTheme.colors.primary)
                }
                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = onLoginClick) {
                    Text("Войти")
                }
            }
        }
    }
}

@Composable
fun RegisterPage(onBackToLoginClick: () -> Unit) {
    Scaffold {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Регистрация", fontSize = 24.sp, modifier = Modifier.padding(bottom = 32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { newEmail -> if (newEmail.length <= MaxLengthAut) email = newEmail },
                maxLines = 1,
                label = { Text("Почта") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { newUsername -> if (newUsername.length <= MaxLengthAut) username = newUsername },
                maxLines = 1,
                label = { Text("Логин") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { newPassword -> if (newPassword.length <= MaxLengthAut) password = newPassword },
                maxLines = 1,
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { newConfirmPassword -> if (newConfirmPassword.length <= MaxLengthAut) confirmPassword = newConfirmPassword },
                maxLines = 1,
                label = { Text("Повторите пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBackToLoginClick) {
                Text("Зарегистрироваться")
            }
        }
    }
}

@Composable
fun TranslatorPage(onLogoutClick: () -> Unit) {
    var isAboutVisible by remember { mutableStateOf(false) }
    var history by remember { mutableStateOf(listOf<TranslationHistoryItem>()) }
    var translationResult by remember { mutableStateOf("") }
    var initialName by remember { mutableStateOf("") }
    var initialFaculty by remember { mutableStateOf("") }

    var selectedPage by remember { mutableStateOf("Home") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Шапка на всю ширину
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(237,247,244))
                .padding(16.dp)
        )  {
            Spacer(modifier = Modifier.width(200.dp))

            Header( onAccountClick = { selectedPage = "Account" } )
        }

        Row(modifier = Modifier.fillMaxSize()) {
            // Боковое меню начинается после шапки
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .background(Color(237,247,244))
            ) {
                TextButton(
                    onClick = { selectedPage = "Home" },
                    modifier = Modifier.fillMaxWidth().background(if (selectedPage == "Home") AccentColor else Color.Transparent)
                        .padding(16.dp),
                    colors = ButtonDefaults.textButtonColors(contentColor = if (selectedPage == "Home") Color.White else Color.Black)
                ) {
                    Text("Главная")
                }
                TextButton(
                    onClick = { selectedPage = "History" },
                    modifier = Modifier.fillMaxWidth().background(if (selectedPage == "History") AccentColor else Color.Transparent)
                        .padding(16.dp),
                    colors = ButtonDefaults.textButtonColors(contentColor = if (selectedPage == "History") Color.White else Color.Black)
                ) {
                    Text("История")
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { isAboutVisible = true },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = ButtonDefaults.textButtonColors(contentColor = if (selectedPage == "About") Color.White else Color.Black)
                ) {
                    Text("О нас")
                }
            }

            // Контент, который будет переключаться в зависимости от выбранной страницы
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (selectedPage) {
                    "Home" -> TranslationForm(
                        onTranslate = { name, faculty ->
                            val result = "Переведенное значение: $name - $faculty"
                            translationResult = result
                            history = history + TranslationHistoryItem(name, faculty, result)
                        },
                        result = translationResult,
                        initialName = initialName,
                        initialFaculty = initialFaculty
                    )
                    "History" -> HistoryPage(
                        history = history,
                        onItemClick = { item ->
                            initialName = item.name
                            initialFaculty = item.faculty
                            translationResult = item.translation
                            selectedPage = "Home"
                        }
                    )
                    "Account" -> AccountPage(
                        onExitClick = { selectedPage = "Login" }
                    )
                    "Login" -> onLogoutClick()
                }
            }
        }
    }

    AboutUsDialog(isVisible = isAboutVisible, onDismiss = { isAboutVisible = false })
}

@Composable
fun Header(onAccountClick: () -> Unit) {
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
                IconButton(
                    onClick = onAccountClick
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Аккаунт",
                        tint = AccentColor,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AccountPage(onExitClick: () -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Страница аккаунта", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.h5)

            Button(
                onClick = onExitClick,
                modifier = Modifier.padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
            ) {
                Text("Выйти", color = Color.White)
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

                    Text("Контактная информация: kryjovnik.info@mail.ru", style = MaterialTheme.typography.body1)
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
                OutlinedTextField(
                    value = name,
                    onValueChange = { newName -> if (newName.length <= 1000) name = newName },
                    maxLines = 7,
                    label = { Text("Название") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = faculty,
                    onValueChange = { newFaculty -> if (newFaculty.length <= 1000) faculty = newFaculty },
                    maxLines = 7,
                    label = { Text("Факультет") },
                    modifier = Modifier.weight(1f)
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
    onItemClick: (TranslationHistoryItem) -> Unit
) {
    MaterialTheme {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("История переводов:", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))

            history.forEach { item ->
                Button(
                    onClick = { onItemClick(item) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
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
