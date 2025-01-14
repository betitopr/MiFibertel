package com.example.mifibertel.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mifibertel.domain.usecase.GetCurrentUserUseCase
import com.example.mifibertel.presentation.auth.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    init {
        getCurrentUser()
    }

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
}