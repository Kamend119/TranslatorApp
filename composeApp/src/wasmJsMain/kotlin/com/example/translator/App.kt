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
import androidx.compose.ui.text.TextStyle
import kotlinx.browser.window

private val LightThemeColors = Colors(
    primary = Color(234, 244, 244),
    primaryVariant = Color(204, 227, 222),
    secondary = Color(64,186,149),
    secondaryVariant = Color(107, 144, 128),
    background = Color(234, 244, 244),
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
    primary = Color(54, 73, 88),
    primaryVariant = Color(85, 130, 139),
    secondary = Color(135, 187, 162),
    secondaryVariant = Color(135, 187, 162),
    background = Color(54, 73, 88),
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

fun copyToClipboard(text: String) {
    window.navigator.clipboard.writeText(text)
}

@Composable
fun App() {
    var isDarkTheme by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf("Login") }

    val email = "example@errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrxample.com"
    val username = "username"

    var expanded by remember { mutableStateOf(false) }

    AppTheme(isDarkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        when(currentPage){
                            "Login" -> Text("Переводчик", fontSize = 30.sp, color = MaterialTheme.colors.onSurface)
                            "Register" -> Text("Регистрация", fontSize = 30.sp, color = MaterialTheme.colors.onSurface)
                            "Translator" -> Text("Главная", fontSize = 30.sp, modifier = Modifier.padding(start = 200.dp), color = MaterialTheme.colors.onSurface)
                            "Account" -> Text("Аккаунт", fontSize = 30.sp, color = MaterialTheme.colors.onSurface)
                        }
                    },
                    actions = {
                        if (isLoggedIn) {
                            IconButton(onClick = { expanded = !expanded }, Modifier.padding(16.dp)) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Аккаунт",
                                    tint = MaterialTheme.colors.secondary,
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                DropdownMenuItem(onClick = {
                                    expanded = false
                                }) {
                                    Text("Мой аккаунт", color = MaterialTheme.colors.onSurface)
                                }

                                DropdownMenuItem(onClick = {
                                    expanded = false
                                }){
                                    OutlinedTextField(
                                        value = email,
                                        onValueChange = {},
                                        label = { Text("Почта") },
                                        readOnly = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp),
                                        textStyle = TextStyle(
                                            color = MaterialTheme.colors.onSurface,
                                            fontSize = 20.sp
                                        ),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = MaterialTheme.colors.secondary,
                                            unfocusedBorderColor = MaterialTheme.colors.onBackground,
                                            cursorColor = MaterialTheme.colors.secondary,
                                            focusedLabelColor = MaterialTheme.colors.secondary,
                                            unfocusedLabelColor = MaterialTheme.colors.onBackground,
                                            backgroundColor = Color.Transparent
                                        )
                                    )
                                }

                                DropdownMenuItem(onClick = {
                                    expanded = false
                                }){
                                    OutlinedTextField(
                                        value = username,
                                        onValueChange = {},
                                        label = { Text("Логин") },
                                        readOnly = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp),
                                        textStyle = TextStyle(
                                            color = MaterialTheme.colors.onSurface,
                                            fontSize = 20.sp
                                        ),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = MaterialTheme.colors.secondary,
                                            unfocusedBorderColor = MaterialTheme.colors.onBackground,
                                            cursorColor = MaterialTheme.colors.secondary,
                                            focusedLabelColor = MaterialTheme.colors.secondary,
                                            unfocusedLabelColor = MaterialTheme.colors.onBackground,
                                            backgroundColor = Color.Transparent
                                        )
                                    )
                                }

                                DropdownMenuItem(onClick = {
                                    isLoggedIn = false
                                    currentPage = "Login"
                                    expanded = false
                                }) {
                                    Text("Выйти", color = MaterialTheme.colors.onSurface)
                                }
                            }
                        }
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { isDarkTheme = it }
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.height(100.dp)
                )
            }
        ) {
            when (currentPage) {
                "Login" -> LoginPage(
                    onLoginClick = {
                        isLoggedIn = true
                        currentPage = "Translator"
                    },
                    onRegisterClick = {
                        currentPage = "Register"
                    }
                )
                "Register" -> RegisterPage(
                    onBackToLoginClick = {
                        currentPage = "Login"
                    }
                )
                "Translator" -> TranslatorPage()
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
                Text("Переводчик", fontSize = 24.sp, modifier = Modifier.padding(bottom = 32.dp), color = MaterialTheme.colors.onSurface)

                OutlinedTextField(
                    value = login,
                    onValueChange = { newLogin -> if (newLogin.length <= 100) login = newLogin },
                    label = { Text("Логин", color = MaterialTheme.colors.onSurface) },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .width(400.dp)
                        .padding(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { newPassword -> if (newPassword.length <= 100) password = newPassword },
                    modifier = Modifier
                        .width(400.dp)
                        .padding(8.dp),
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
                    modifier = Modifier
                        .width(400.dp)
                        .padding(8.dp),
                    label = { Text("Почта", color = MaterialTheme.colors.onSurface) },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { newUsername -> if (newUsername.length <= 100) username = newUsername },
                    modifier = Modifier
                        .width(400.dp)
                        .padding(8.dp),
                    label = { Text("Логин", color = MaterialTheme.colors.onSurface) },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { newPassword -> if (newPassword.length <= 100) password = newPassword },
                    modifier = Modifier
                        .width(400.dp)
                        .padding(8.dp),
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
                    modifier = Modifier
                        .width(400.dp)
                        .padding(8.dp),
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
fun TranslatorPage() {
    var isAboutVisible by remember { mutableStateOf(false) }
    var history by remember { mutableStateOf(listOf<TranslationHistoryItem>()) }
    var translationResult by remember { mutableStateOf("") }
    var initialName by remember { mutableStateOf("") }
    var initialFaculty by remember { mutableStateOf("") }
    var initialDirection by remember { mutableStateOf("") }

    var selectedPage by remember { mutableStateOf("Home") }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
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
                        Text("Главная", color = MaterialTheme.colors.onSurface)
                    }
                    TextButton(
                        onClick = { selectedPage = "History" },
                        modifier = Modifier.fillMaxWidth().background(if (selectedPage == "History") MaterialTheme.colors.secondary else Color.Transparent)
                            .padding(16.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary)
                    ) {
                        Text("История", color = MaterialTheme.colors.onSurface)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { isAboutVisible = true },
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary)
                    ) {
                        Text("О нас", color = MaterialTheme.colors.onSurface)
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
                    }
                }
            }
        }
    }

    AboutUsDialog(isVisible = isAboutVisible, onDismiss = { isAboutVisible = false })
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
                    Text("О нас", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onSurface)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Команда переводчика:\nБобровников Никита\nЕвстигнеев Александр\nЗайниев Владислав" +
                            "\nИшкова Анна\nМикрюкова Анастасия", style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Контактная информация: kryjovnik.info@mail.ru", style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface)
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
fun TranslationForm(onTranslate: (String, String, String) -> Unit, result: String, initialName: String = "",
                    initialFaculty: String = "", initialDirection: String = "") {

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
                    label = { Text("Название", color = MaterialTheme.colors.onSurface) },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent
                    )
                )

                OutlinedTextField(
                    value = faculty,
                    onValueChange = { newFaculty -> if (newFaculty.length <= 1000) faculty = newFaculty },
                    label = { Text("Факультет", color = MaterialTheme.colors.onSurface) },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent
                    )
                )

                OutlinedTextField(
                    value = direction,
                    onValueChange = { newDirection -> if (newDirection.length <= 1000) direction = newDirection },
                    label = { Text("Направление", color = MaterialTheme.colors.onSurface) },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Перевод", style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onSurface)

                OutlinedTextField(
                    value = result,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        disabledBorderColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent
                    )
                )
            }

            Button(
                onClick = {
                    copyToClipboard(result)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text("Скопировать результат", color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}

@Composable
fun HistoryPage(history: List<TranslationHistoryItem>, onItemClick: (TranslationHistoryItem) -> Unit
) {
    MaterialTheme {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("История переводов:", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onSurface)
            Spacer(modifier = Modifier.height(8.dp))

            history.forEach { item ->
                Button(
                    onClick = { onItemClick(item) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Перевод: ${item.translation}", style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    }
}
