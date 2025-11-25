package com.example.piyo.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.piyo.presentation.home.beranda.BerandaScreen
import com.example.piyo.presentation.home.parent.ChatBotScreen
import com.example.piyo.presentation.home.settings.KeamananIzinScreen
import com.example.piyo.presentation.home.settings.SettingScreen
import com.example.piyo.ui.theme.Black

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navigateLogin: () -> Unit
) {
    val navController = rememberNavController()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedItem,
                onItemClick = {
                    selectedItem = it
                    when (it) {
                        0 -> navigateToTab(navController, HomeRoute)
                        1 -> navigateToTab(navController, PiyoParentRoute)
                        2 -> navigateToTab(navController, PiyoPlanRoute)
                        3 -> navigateToTab(navController, SettingsRoute)
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                BerandaScreen(navController = navController)
            }

            composable<ChatbotRoute> {
                ChatBotScreen(navController = navController)
            }

            composable<PiyoParentRoute> {
                PlaceholderScreen("Piyo Parent")
            }

            composable<PiyoPlanRoute> {
                PlaceholderScreen("Piyo Plan")
            }

            composable<QuizRoute> {
                // placeholder until quiz screen is implemented by another dev
                PlaceholderScreen("Kuis")
            }

            composable<SettingsRoute> {
                SettingScreen(
                    navController = navController,
                    onLogout = navigateLogin
                )
            }

            composable<KeamananIzinRoute> {
                KeamananIzinScreen(navController = navController)
            }

            composable<NotifikasiRoute> {
                com.example.piyo.presentation.home.notifications.NotifikasiScreen(navController = navController)
            }
            composable<InsightRoute> {
                com.example.piyo.presentation.home.insight.InsightScreen(navController = navController)
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: AppRoute) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Screen: $title", color = Black)
    }
}
