package com.example.mifibertel.presentation.auth.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mifibertel.domain.model.User
import com.example.mifibertel.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
/**Pantalla principal de perfil que utiliza ViewModel para gestionar el estado
 * */
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(), // Inyección del ViewModel
    onNavigateBack: () -> Unit // Callback para navegación
) {
    // Observar el estado del perfil usando collectAsState
    val userProfileState = viewModel.userProfile.collectAsState()

    Scaffold(
        // Barra superior con título y botón de regreso
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Contenedor principal que maneja los diferentes estados
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Manejo de estados usando when
            when (val profileState = userProfileState.value) {
                // Estado de carga: muestra un indicador de progreso
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // Estado de éxito: muestra el contenido del perfil
                is Resource.Success -> {
                    ProfileContent(user = profileState.data)
                }
                // Estado de error: muestra mensaje de error
                is Resource.Error -> {
                    Text(
                        text = profileState.message ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

// Composable que muestra el contenido del perfil
@Composable
fun ProfileContent(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Avatar circular con la inicial del nombre
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.firstName.firstOrNull()?.toString() ?: "",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campos del perfil
        ProfileField("Nombre", "${user.firstName} ${user.lastName}")
        ProfileField("Usuario", user.username)
        ProfileField("Email", user.email)
        ProfileField("Teléfono", user.phoneNumber)
        ProfileField("Tipo de Usuario", if (user.isClient) "Cliente" else "Usuario")
    }
}

// Composable reutilizable para mostrar cada campo del perfil
@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        // Etiqueta del campo
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        // Valor del campo
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
        // Línea divisoria
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}