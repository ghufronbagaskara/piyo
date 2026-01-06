package com.example.piyo.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.piyo.presentation.splash.SplashScreen
import com.example.piyo.presentation.tutorial.TutorialScreen
import com.example.piyo.presentation.auth.login.LoginScreen
import com.example.piyo.presentation.auth.register.RegisterScreen
import com.example.piyo.presentation.home.parent.ChatBotScreen
import com.example.piyo.presentation.infoanak.InfoAnakScreen
import com.example.piyo.presentation.quiz.QuizIntroductionScreen
import com.example.piyo.presentation.quiz.QuizQuestionScreen
import com.example.piyo.presentation.quiz.QuizResultScreen

@Composable
fun TutorialNavigation(
    modifier: Modifier = Modifier,
    startDestination: AppRoute
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable<SplashRoute> { SplashScreen(navController) }
        composable<OnboardingRoute> { TutorialScreen(navController) }
        composable<LoginRoute> { LoginScreen(navController) }
        composable<RegisterRoute> { RegisterScreen(navController) }
        composable<InfoAnakRoute> { InfoAnakScreen(navController) }
        composable<ChatbotRoute> { ChatBotScreen(navController) }

        // Quiz routes
        composable<QuizIntroductionRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<QuizIntroductionRoute>()
            QuizIntroductionScreen(
                navController = navController,
                childAge = route.childAge,
                childId = route.childId
            )
        }

        composable<QuizQuestionRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<QuizQuestionRoute>()
            QuizQuestionScreen(
                navController = navController,
                childAge = route.childAge,
                childId = route.childId
            )
        }

        composable<QuizResultRoute> {
            QuizResultScreen(navController = navController)
        }

        composable<MainRoute> {
            MainNavigation(
                navigateLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(MainRoute) { inclusive = true }
                    }
                }
            )
        }
    }
}
