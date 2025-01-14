package com.example.mifibertel.presentation.auth.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mifibertel.data.api.ApiService
import com.example.mifibertel.domain.model.User
import com.example.mifibertel.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * Clase que se encarga de manejar la información del perfil del usuario
 */
@HiltViewModel
// ViewModel especializado en manejar la información del perfil del usuario
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,  // Servicio para llamadas a la API
) : ViewModel() {

    // Estado privado mutable para el perfil del usuario usando sealed class Resource
    private val _userProfile = MutableStateFlow<Resource<User>>(Resource.Loading())

    // Estado público inmutable que se observará en la UI
    val userProfile = _userProfile.asStateFlow()

    // Se ejecuta al crear la instancia del ViewModel
    init {
        getUserProfile()  // Carga automática del perfil
    }

    // Función privada que obtiene el perfil del usuario de la API
    private fun getUserProfile() {
        viewModelScope.launch {
            try {
                val response = apiService.getCurrentUser()

                if (response.isSuccessful) {
                    response.body()?.let { userDto ->
                        _userProfile.value = Resource.Success(userDto.toDomain())
                    }
                } else {
                    _userProfile.value = Resource.Error("Error al obtener el perfil")
                }
            } catch (e: Exception) {
                _userProfile.value = Resource.Error(e.message ?: "Error desconocido")
            }
        }
    }
}