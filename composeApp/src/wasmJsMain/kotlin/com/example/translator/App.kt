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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

private val LightThemeColors = Colors(
    primary = Color(234, 244, 244), // основной цвет
    primaryVariant = Color(204, 227, 222), // дополнительный акцент
    secondary = Color(162,235,213), // акцентный цвет (для кнопок)
    secondaryVariant = Color(107, 144, 128),
    background = Color(234, 244, 244), // фон приложения
    surface = Color.White,
    error = Color.Red,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
    isLight = true
)

private val DarkThemeColors = Colors(
    primary = Color(54, 73, 88), // основной цвет
    primaryVariant = Color(85, 130, 139), // дополнительный акцент
    secondary = Color(135, 187, 162), // акцентный цвет (для кнопок)
    secondaryVariant = Color(135, 187, 162),
    background = Color(54, 73, 88), // фон приложения
    surface = Color.DarkGray,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,
    isLight = false
)

data class TranslationHistoryItem(
    val name: String,
    val faculty: String,
    val translation: String,
    val direction: String
)

@Composable
fun AppTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors
    MaterialTheme(colors = colors, content = content)
}

@Composable
fun App() {
    var isDarkTheme by remember { mutableStateOf(false) }

    AppTheme(isDarkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Переводчик") },
                    actions = {
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { isDarkTheme = it }
                        )
                    }
                )
            }
        ) {
            var currentPage by remember { mutableStateOf("Login") }
            when (currentPage) {
                "Login" -> LoginPage(
                    onLoginClick = { currentPage = "Translator" },
                    onRegisterClick = { currentPage = "Register" }
                )
                "Register" -> RegisterPage(
                    onBackToLoginClick = { currentPage = "Login" }
                )
                "Translator" -> TranslatorPage(onLogoutClick = { currentPage = "Login" })
            }
        }
    }
}

@Composable
fun LoginPage(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold {
        MaterialTheme {
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
                    label = { Text("Логин", color = MaterialTheme.colors.onSurface) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { newPassword -> if (newPassword.length <= 100) password = newPassword },
                    maxLines = 1,
                    label = { Text("Пароль", color = MaterialTheme.colors.onSurface) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = onRegisterClick,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Регистрация", color = MaterialTheme.colors.onPrimary)
                    }

                    Button(
                        onClick = onLoginClick, Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                    ) {
                        Text("Войти", color = MaterialTheme.colors.onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterPage(onBackToLoginClick: () -> Unit) {
    Scaffold {
        MaterialTheme {
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
                    label = { Text("Почта", color = MaterialTheme.colors.onSurface) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { newUsername -> if (newUsername.length <= 100) username = newUsername },
                    maxLines = 1,
                    label = { Text("Логин", color = MaterialTheme.colors.onSurface) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { newPassword -> if (newPassword.length <= 100) password = newPassword },
                    maxLines = 1,
                    label = { Text("Пароль", color = MaterialTheme.colors.onSurface) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { newConfirmPassword -> if (newConfirmPassword.length <= 100) confirmPassword = newConfirmPassword },
                    maxLines = 1,
                    label = { Text("Повторите пароль", color = MaterialTheme.colors.onSurface) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onBackToLoginClick,
                    modifier = Modifier.padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                ) {
                    Text("Зарегистрироваться", color = MaterialTheme.colors.onPrimary)
                }
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

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primaryVariant)
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
                        .background(MaterialTheme.colors.primaryVariant)
                ) {
                    TextButton(
                        onClick = { selectedPage = "Home" },
                        modifier = Modifier.fillMaxWidth().background(if (selectedPage == "Home") MaterialTheme.colors.secondary else Color.Transparent)
                            .padding(16.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary)
                    ) {
                        Text("Главная")
                    }
                    TextButton(
                        onClick = { selectedPage = "History" },
                        modifier = Modifier.fillMaxWidth().background(if (selectedPage == "History") MaterialTheme.colors.secondary else Color.Transparent)
                            .padding(16.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary)
                    ) {
                        Text("История")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { isAboutVisible = true },
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary)
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
                        tint = MaterialTheme.colors.secondary,
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
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text("Выйти", color = MaterialTheme.colors.onPrimary)
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
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                    ) {
                        Text("Закрыть", color = MaterialTheme.colors.onPrimary)
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
                    label = { Text("Название", color = MaterialTheme.colors.onSurface) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )

                OutlinedTextField(
                    value = faculty,
                    onValueChange = { newFaculty -> if (newFaculty.length <= 1000) faculty = newFaculty },
                    maxLines = 7,
                    label = { Text("Факультет", color = MaterialTheme.colors.onSurface) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )

                OutlinedTextField(
                    value = direction,
                    onValueChange = { newDirection -> if (newDirection.length <= 1000) direction = newDirection },
                    maxLines = 7,
                    label = { Text("Направление", color = MaterialTheme.colors.onSurface) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground
                    )
                )
            }

            Button(
                onClick = { onTranslate(name, faculty, direction) },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text("Перевести", color = MaterialTheme.colors.onPrimary)
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
