package com.example.mifibertel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mifibertel.data.local.TokenManager
import com.example.mifibertel.data.repository.AuthRepository
import com.example.mifibertel.domain.usecase.GetCurrentUserUseCase
import com.example.mifibertel.domain.usecase.LoginUseCase
import com.example.mifibertel.domain.usecase.RefreshTokenUseCase
import com.example.mifibertel.navigation.AppNavigation
import com.example.mifibertel.presentation.auth.AuthScreen
import com.example.mifibertel.presentation.auth.AuthViewModel
import com.example.mifibertel.ui.theme.MiFibertelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MiFibertelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Importamos nuestro NavHost
                    AppNavigation()
                }
            }
        }
    }
}

