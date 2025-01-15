package com.example.mifibertel.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mifibertel.domain.model.Invoice
import com.example.mifibertel.domain.model.Ticket
import com.example.mifibertel.domain.usecase.GetCurrentUserUseCase
import com.example.mifibertel.domain.usecase.GetInvoicesUseCase
import com.example.mifibertel.domain.usecase.GetTicketsUseCase
import com.example.mifibertel.presentation.auth.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * ViewModel para nuestra Screen osea Pantalla Home que gestiona loss estadoss de usuario,facturass y tickets
 * */
@HiltViewModel
class HomeViewModel @Inject constructor(
    // Inyección de casos de uso necesarios
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getInvoicesUseCase: GetInvoicesUseCase,
    private val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {
    // Estados mutables privados
    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    private val _invoicesState = MutableStateFlow<InvoicesState>(InvoicesState.Loading)
    private val _ticketsState = MutableStateFlow<TicketsState>(TicketsState.Loading)

    // Estados inmutables públicos para la UI
    val userState = _userState.asStateFlow()
    val invoicesState = _invoicesState.asStateFlow()
    val ticketsState = _ticketsState.asStateFlow()

    // Inicialización del ViewModel
    init {
        getCurrentUser()
        loadAllData()// Cargar todos los datos al crear el ViewModel
    }
    /**
     * Carga todos los datos necesarios para la pantalla Home
     */
    private fun loadAllData() {
        getCurrentUser()
        getInvoices()
        getTickets()
    }
    /**
     * Obtiene información del usuario actual
     */
    private fun getCurrentUser() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            getCurrentUserUseCase()
                .onSuccess { user ->
                    _userState.value = UserState.Success(user)
                }
                .onFailure { exception ->
                    _userState.value = UserState.Error(exception.message ?: "Unknown error")
                }
        }
    }
    /**
     * Obtiene la lista de facturas
     */
    private fun getInvoices() {
        viewModelScope.launch {
            getInvoicesUseCase()
                .onSuccess { invoices -> _invoicesState.value = InvoicesState.Success(invoices) }
                .onFailure { error -> _invoicesState.value = InvoicesState.Error(error.message ?: "") }
        }
    }
    /**
     * Obtiene la lista de tickets
     */
    private fun getTickets() {
        viewModelScope.launch {
            getTicketsUseCase()
                .onSuccess { tickets -> _ticketsState.value = TicketsState.Success(tickets) }
                .onFailure { error -> _ticketsState.value = TicketsState.Error(error.message ?: "") }
        }
    }
}
/**
 * Estados posibles para las facturas
 */
sealed class InvoicesState {
    object Loading : InvoicesState()
    data class Success(val invoices: List<Invoice>) : InvoicesState()
    data class Error(val message: String) : InvoicesState()
}
/**
 * Estados posibles para los tickets
 */
sealed class TicketsState {
    object Loading : TicketsState()
    data class Success(val tickets: List<Ticket>) : TicketsState()
    data class Error(val message: String) : TicketsState()
}
