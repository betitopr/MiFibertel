package com.example.mifibertel.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mifibertel.presentation.auth.UserState
import com.example.mifibertel.presentation.home.InvoicesState
import com.example.mifibertel.presentation.home.TicketsState

@Composable
fun WelcomeHeader(userState: UserState) {
    when (userState) {
        is UserState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "¡Hola, ${userState.user.firstName}!",
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
        else -> {} // Manejar error si es necesario
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsCard(
    invoicesState: InvoicesState,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Payment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Estado de Pagos",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (invoicesState) {
                is InvoicesState.Success -> {
                    val pendingInvoices = invoicesState.invoices.filter { it.status == "pending" }
                    if (pendingInvoices.isNotEmpty()) {
                        Text(
                            text = "Tienes ${pendingInvoices.size} factura(s) pendiente(s)",
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Próximo vencimiento: ${pendingInvoices.first().dueDate}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        Text(
                            text = "No tienes facturas pendientes",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                is InvoicesState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is InvoicesState.Error -> {
                    Text(
                        text = "Error al cargar facturas",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsCard(
    ticketsState: TicketsState,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.SupportAgent,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Tickets de Soporte",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (ticketsState) {
                is TicketsState.Success -> {
                    val openTickets = ticketsState.tickets.filter { it.status == "open" }
                    if (openTickets.isNotEmpty()) {
                        Text(
                            text = "Tienes ${openTickets.size} ticket(s) abierto(s)",
                            color = MaterialTheme.colorScheme.primary
                        )
                        openTickets.firstOrNull()?.let { ticket ->
                            Text(
                                text = "Último ticket: ${ticket.title}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Estado: ${ticket.status.capitalize()}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    } else {
                        Text(
                            text = "No tienes tickets abiertos",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                is TicketsState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is TicketsState.Error -> {
                    Text(
                        text = "Error al cargar tickets",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

