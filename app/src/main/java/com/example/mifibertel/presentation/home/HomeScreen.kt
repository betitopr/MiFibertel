package com.example.mifibertel.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mifibertel.R
import com.example.mifibertel.navigation.NavRoutes
import com.example.mifibertel.presentation.auth.UserState
import com.example.mifibertel.presentation.components.PaymentsCard
import com.example.mifibertel.presentation.components.TicketsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToPayments: () -> Unit,
    onNavigateToTickets: () -> Unit,
    onLogout: () -> Unit
) {
    // Observar estados del ViewModel usando collectAsState
    val userState by viewModel.userState.collectAsState()
    val invoicesState by viewModel.invoicesState.collectAsState()
    val ticketsState by viewModel.ticketsState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,

                    ){
                        Image(painter = painterResource(id = R.drawable.logo),
                            contentDescription ="Logo pequeño" ,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(width = 200.dp, height = 100.dp) // Reducir tamaño
                                .padding(start = 8.dp) // Añadir padding izquierdo
                        )
//                        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre la imagen y el texto
//                        Text(text = "MiFibertel")

                    }
                },
                actions = {
                    IconButton(onClick =  {
                        navController.navigate(NavRoutes.PROFILE)
                    }
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }

                },

            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            // Saludo personalizado
            item {
                when (userState) {
                    is UserState.Success -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "¡Hola, ${(userState as UserState.Success).user.firstName}!",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Bienvenido a tu panel de control",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    is UserState.Loading -> {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                    else -> {}
                }
            }

            // Cards
            item {
                PaymentsCard(
                    invoicesState = invoicesState,
                    onClick = onNavigateToPayments
                )
            }

            item {
                TicketsCard(
                    ticketsState = ticketsState,
                    onClick = onNavigateToTickets
                )
            }

            // Espacio adicional al final
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}











@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
        onNavigateToPayments = {},
        onNavigateToTickets = {},
        onLogout = {})
}