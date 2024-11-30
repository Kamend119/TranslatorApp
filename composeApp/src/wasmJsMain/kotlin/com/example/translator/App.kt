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

val AccentColor = Color(62,180,137)
var AdditionalAccent = Color(237,247,244)

data class TranslationHistoryItem(
    val name: String,
    val faculty: String,
    val translation: String,
    val direction: String
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
            onBackToLoginClick = { currentPage = "Login" }
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
                onValueChange = { newLogin -> if (newLogin.length <= 100) login = newLogin },
                maxLines = 1,
                label = { Text("Логин") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { newPassword -> if (newPassword.length <= 100) password = newPassword },
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
                TextButton(onClick = onRegisterClick, Modifier.padding(8.dp)) {
                    Text("Регистрация", color = MaterialTheme.colors.primary)
                }

                Button(
                    onClick = onLoginClick, Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
                ) {
                    Text("Войти", color = Color.White)
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
                onValueChange = { newEmail -> if (newEmail.length <= 100) email = newEmail },
                maxLines = 1,
                label = { Text("Почта") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { newUsername -> if (newUsername.length <= 100) username = newUsername },
                maxLines = 1,
                label = { Text("Логин") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { newPassword -> if (newPassword.length <= 100) password = newPassword },
                maxLines = 1,
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { newConfirmPassword -> if (newConfirmPassword.length <= 100) confirmPassword = newConfirmPassword },
                maxLines = 1,
                label = { Text("Повторите пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBackToLoginClick,
                modifier = Modifier.padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
            ) {
                Text("Зарегистрироваться", color = Color.White)
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
    var initialDirection by remember { mutableStateOf("") }

    var selectedPage by remember { mutableStateOf("Home") }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AdditionalAccent)
                .padding(16.dp)
        )  {
            Spacer(modifier = Modifier.width(200.dp))

            Header( onAccountClick = { selectedPage = "Account" } )
        }

        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .background(AdditionalAccent)
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (selectedPage) {
                    "Home" -> TranslationForm(
                        onTranslate = { name, faculty, direction ->
                            val result = "Переведенное значение: $name - $faculty - $direction"
                            translationResult = result
                            history = history + TranslationHistoryItem(name, faculty, result, direction)
                        },
                        result = translationResult,
                        initialName = initialName,
                        initialFaculty = initialFaculty,
                        initialDirection = initialDirection
                    )
                    "History" -> HistoryPage(
                        history = history,
                        onItemClick = { item ->
                            initialName = item.name
                            initialFaculty = item.faculty
                            initialDirection = item.direction
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

                    Text("Команда переводчика:\nБобровников Никита\nЕвстигнеев Александр\nЗайниев Владислав" +
                            "\nИшкова Анна\nМикрюкова Анастасия", style = MaterialTheme.typography.body1)
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
    onTranslate: (String, String, String) -> Unit,
    result: String,
    initialName: String = "",
    initialFaculty: String = "",
    initialDirection: String = ""
) {
    var name by remember { mutableStateOf(initialName) }
    var faculty by remember { mutableStateOf(initialFaculty) }
    var direction by remember { mutableStateOf(initialDirection) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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

                OutlinedTextField(
                    value = direction,
                    onValueChange = { newDirection -> if (newDirection.length <= 1000) direction = newDirection },
                    maxLines = 7,
                    label = { Text("Направление") },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = { onTranslate(name, faculty, direction) },
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
                    // при длинных, он криво рисует
                    Column(Modifier.padding(8.dp)) {
                        Text("Перевод: ${item.translation}", style = MaterialTheme.typography.body1)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Название: ${item.name}", style = MaterialTheme.typography.body2)
                            Text("Факультет: ${item.faculty}", style = MaterialTheme.typography.body2)
                            Text("Направление: ${item.direction}", style = MaterialTheme.typography.body2)
                        }
                    }
                }
            }
        }
    }
}
