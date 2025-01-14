package com.example.mifibertel.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mifibertel.R
import com.example.mifibertel.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    // Estados locales para los campos de entrada
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Estados del ViewModel observados
    val authState by viewModel.authState.collectAsState()
    val userState by viewModel.userState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),// Aumentamos el padding horizontal
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(0.3f)) // Espacio superior flexible
        // Logo con posición ajustada
        Image(
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = "Logo Mi Fibertel",
            modifier = Modifier
                .size(280.dp,200.dp)  // Ajustar height específicamente
                .clip(RectangleShape)  // Recorta cualquier contenido que exceda el tamaño
        )

        // UI para login
        LoginSection(
            username = username,
            onUsernameChange = { username = it },
            password = password,
            onPasswordChange = { password = it },
            onLoginClick = { viewModel.login(username, password) },
            isLoading = authState is AuthState.Loading
        )

        Spacer(modifier = Modifier.weight(0.2f))// Espacio inferior flexible


        // Mostrar información del usuario cuando está disponible
        when (val state = userState) {
            is UserState.Success -> {
                UserInfoSection(user = state.user)
            }
            is UserState.Error -> {
                ErrorMessage(message = state.message)
            }
            is UserState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary,
//                    strokeWidth = 4.dp
                )
            }
            else -> {}
        }

        // Manejar estados de autenticación
        LaunchedEffect(authState) {
            when (authState) {
                is AuthState.Success -> onLoginSuccess()
                is AuthState.Error -> {

                }
                else -> {}
            }
        }
    }
}

@Composable
private fun LoginSection(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Campo de usuario
        CustomTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = "Usuario",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFE3F2FD) // Color celeste pastel
        )

        // Campo de contraseña
        CustomPasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFE3F2FD) // Color celeste pastel
        )


        Button(
            onClick = onLoginClick,
            enabled = !isLoading && username.isNotEmpty() && password.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp), // Altura fija para el botón
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3) // Color azul principal
            ),
            shape = RoundedCornerShape(8.dp) // Bordes redondeados

        ) {
            if (isLoading) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
//                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cargando...",
                        color = Color.White,
                        fontSize = 16.sp)
                }
            } else {
                Text("Iniciar Sesion",
                    color = Color.White,
                    fontSize = 16.sp)
            }
        }
        Row {
            Text(
                text = "¿Olvidaste tu contraseña?"
            )
             Text(
                 //Texto en negrita para resaltar
                 text = " recuperala",
                 fontWeight = FontWeight.Bold

             )
        }

    }
}

@Composable
private fun UserInfoSection(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium
            )
            if (user.phoneNumber.isNotEmpty()) {
                Text(
                    text = user.phoneNumber,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)

    )
}
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    enabled: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
            // Quitar todos los bordes excepto el inferior
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
            // Hacer transparentes los bordes laterales y superior
            focusedLeadingIconColor = Color.Transparent,
            focusedTrailingIconColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Transparent,
            unfocusedTrailingIconColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(5.dp)
    )
}
@Composable
fun CustomPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
            focusedLeadingIconColor = Color.Transparent,
//            focusedTrailingIconColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Transparent,
//            unfocusedTrailingIconColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(5.dp),
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Default.VisibilityOff
                    else
                        Icons.Default.Visibility,
                    contentDescription = if (passwordVisible)
                        "Ocultar contraseña"
                    else
                        "Mostrar contraseña"
                )
            }
        }
    )
}
@Preview(showBackground = true)
@Composable
fun LoginSectionPreview() {
    LoginSection(
        username = "Peponcio",
        onUsernameChange = {},
        password = "programadorx-12",
        onPasswordChange = {},
        onLoginClick = {},
        isLoading = false // Cambia a true para ver el estado de carga
    )
}
