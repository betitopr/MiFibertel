package com.example.mifibertel.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mifibertel.R

//Logica de la pantalla de splash
@Composable
fun SplashScreen() {
    Splash()
}

@Composable
fun Splash() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Espacio superior flexible
            Spacer(modifier = Modifier.weight(1f))

            // Logo centrado
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Mi Fibertel",
                modifier = Modifier
                    .size(310.dp)
            )

            // Espacio flexible entre logo y texto
            Spacer(modifier = Modifier.weight(1f))

            // Texto cerca del final
            Text(
                text = "Gestiona tu internet en un solo click",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 60.dp) // Espacio desde el final
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = "id:pixel_4"
)
@Composable
fun SplashScreenPreview() {
    Splash()
}