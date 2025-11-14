package com.example.piyo.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.piyo.R
import com.example.piyo.presentation.navigation.OnboardingRoute
import com.example.piyo.ui.theme.PiyoTheme
import com.example.piyo.ui.theme.SplashBg
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        delay(2500)
        navController.navigate(OnboardingRoute) { popUpTo(0) }
    }

    PiyoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplashBg),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_piyo_logo),
                    contentDescription = "Piyo logo",
                    modifier = Modifier.size( 110.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.splash_text),
                    contentDescription = "Piyo text",
                    modifier = Modifier.size(120.dp)
                )
            }
        }
    }
}
