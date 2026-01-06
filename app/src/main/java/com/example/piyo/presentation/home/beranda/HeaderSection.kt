package com.example.piyo.presentation.home.beranda

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HeaderSection(
    navController: NavController,
    userName: String = "Pengguna",
    userPhotoUrl: String = "",
    onSearch: (String) -> Unit = {}
) {
    ProfileHeader(
        name = userName,
        photoUrl = userPhotoUrl,
        onNotificationClick = {
            navController.navigate(com.example.piyo.presentation.navigation.NotifikasiRoute)
        },
        onSearch = onSearch
    )
}
