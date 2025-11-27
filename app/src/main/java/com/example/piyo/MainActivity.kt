package com.example.piyo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.example.piyo.presentation.navigation.MainRoute
import com.example.piyo.presentation.navigation.SplashRoute
import com.example.piyo.presentation.navigation.TutorialNavigation
import com.example.piyo.seed.EducationSeeder
import com.example.piyo.ui.theme.PiyoTheme
import com.example.piyo.util.FirebaseUtils
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        EducationSeeder.seed()
        setContent {
            PiyoTheme {
                val startDestination = if (FirebaseUtils.isUserLoggedIn()) MainRoute else SplashRoute
                // TransparentSystemBars()
                TutorialNavigation(startDestination = startDestination)
            }
        }
    }
}

@Composable
private fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
    }
}