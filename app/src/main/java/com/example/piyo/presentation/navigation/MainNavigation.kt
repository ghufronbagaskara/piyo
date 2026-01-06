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
import androidx.navigation.toRoute
import com.example.piyo.presentation.home.beranda.BerandaScreen
import com.example.piyo.presentation.home.parent.ChatBotScreen
import com.example.piyo.presentation.home.notifications.NotifikasiScreen
import com.example.piyo.presentation.home.insight.InsightScreen
import com.example.piyo.presentation.home.parent.PiyoParentScreen
import com.example.piyo.presentation.home.settings.SettingScreen
import com.example.piyo.presentation.piyoplan.PiyoPlanScreen
import com.example.piyo.ui.theme.Black
import org.koin.androidx.compose.koinViewModel

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
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                BerandaScreen(navController = navController)
            }

            composable<NotifikasiRoute> {
                NotifikasiScreen(navController = navController)
            }

            composable<InsightRoute> {
                InsightScreen(navController = navController)
            }

            composable<ChatbotRoute> {
                ChatBotScreen(navController = navController)
            }

            composable<PiyoParentRoute> {
                // Get BerandaViewModel to access child data
                val berandaViewModel: com.example.piyo.presentation.home.beranda.BerandaViewModel = koinViewModel()
                val berandaState = berandaViewModel.uiState

                PiyoParentScreen(
                    onNavigateToChatBot = {
                        navigateToTab(navController, ChatbotRoute)
                    },
                    onNavigateToContentDetail = { contentId: String ->
                        // TODO: Navigate to content detail screen when implemented
                    },
                    onNavigateToQuizDetail = { quizId: String ->
                        // Navigate to quiz using child data
                        berandaState.selectedChild?.let { child ->
                            val childAge = calculateChildAge(child.birthDate)
                            navController.navigate(
                                QuizQuestionRoute(
                                    childAge = childAge,
                                    childId = child.id
                                )
                            )
                        } ?: run {
                            // If no child data, navigate to add child screen
                            navController.navigate(InfoAnakRoute)
                        }
                    }
                )
            }

            composable<PiyoPlanRoute> {
                PiyoPlanScreen()
            }

            composable<SettingsRoute> {
                SettingScreen(
                    navController = navController,
                    onLogout = navigateLogin
                )
            }

            // Quiz routes - Add these to MainNavigation
            composable<QuizIntroductionRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<QuizIntroductionRoute>()
                com.example.piyo.presentation.quiz.QuizIntroductionScreen(
                    navController = navController,
                    childAge = route.childAge,
                    childId = route.childId
                )
            }

            composable<QuizQuestionRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<QuizQuestionRoute>()
                com.example.piyo.presentation.quiz.QuizQuestionScreen(
                    navController = navController,
                    childAge = route.childAge,
                    childId = route.childId
                )
            }

            composable<QuizResultRoute> {
                com.example.piyo.presentation.quiz.QuizResultScreen(navController = navController)
            }
        }
    }
}

// Helper function to calculate child age
private fun calculateChildAge(birthDate: String): Int {
    return try {
        val parts = birthDate.split("-")
        if (parts.size == 3) {
            val birthYear = parts[0].toInt()
            val birthMonth = parts[1].toInt()
            val birthDay = parts[2].toInt()

            val today = java.util.Calendar.getInstance()
            val currentYear = today.get(java.util.Calendar.YEAR)
            val currentMonth = today.get(java.util.Calendar.MONTH) + 1
            val currentDay = today.get(java.util.Calendar.DAY_OF_MONTH)

            var age = currentYear - birthYear

            if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
                age--
            }

            age
        } else {
            5 // Default age
        }
    } catch (e: Exception) {
        5 // Default age
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
