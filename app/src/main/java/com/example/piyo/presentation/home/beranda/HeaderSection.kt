package com.example.piyo.presentation.home.beranda

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HeaderSection(navController: NavController) {
    ProfileHeader(
        onNotificationClick = {
            navController.navigate(com.example.piyo.presentation.navigation.NotifikasiRoute)
        }
    )
}
