package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.piyo.ui.theme.Sora
import androidx.navigation.NavController
import com.example.piyo.presentation.navigation.ChatbotRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun BerandaScreen(
    navController: NavController,
    viewModel: BerandaViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF0277BD)
                )
            }
            uiState.error != null -> {
                Text(
                    text = uiState.error,
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        HeaderSection(
                            navController = navController,
                            userName = uiState.userName,
                            userPhotoUrl = uiState.userPhotoUrl,
                            onSearch = viewModel::onSearch
                        )
                    }
                    item { QuizTitleSection() }
                    item { QuizSection(navController, viewModel) }
                    item {
                        InsightSection(
                            navController = navController,
                            childName = uiState.selectedChild?.fullName ?: "Anak"
                        )
                    }
                    item { BelajarSection() }

                    // filler agar scroll lebih nyaman
                    items((1..5).toList()) {
                        Spacer(Modifier.height(20.dp))
                    }
                }

                WelcomePopupWrapper(
                    showWelcome = uiState.showWelcomePopup,
                    userName = uiState.userName,
                    onDismiss = viewModel::dismissWelcomePopup
                )

                ChatbotFab(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun QuizTitleSection() {
    Text(
        text = "Coba kuis sekarang",
        fontFamily = Sora,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = androidx.compose.ui.graphics.Color.Black,
        modifier = Modifier.padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 8.dp)
    )
}

@Composable
private fun QuizSection(navController: NavController, viewModel: BerandaViewModel) {
    val uiState = viewModel.uiState
    val selectedChild = uiState.selectedChild

    QuizBanner(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        onCtaClick = {
            selectedChild?.let { child ->
                // Calculate child age from birthDate
                val childAge = calculateAge(child.birthDate)
                // Navigate directly to quiz question screen
                navController.navigate(
                    com.example.piyo.presentation.navigation.QuizQuestionRoute(
                        childAge = childAge,
                        childId = child.id
                    )
                )
            } ?: run {
                // If no child data, show message or navigate to add child
                navController.navigate(com.example.piyo.presentation.navigation.InfoAnakRoute)
            }
        }
    )
}

// Helper function to calculate age from birthDate
private fun calculateAge(birthDate: String): Int {
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

            // Adjust if birthday hasn't occurred this year
            if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
                age--
            }

            age
        } else {
            5 // Default age if parsing fails
        }
    } catch (e: Exception) {
        5 // Default age if error occurs
    }
}

@Composable
private fun InsightSection(
    navController: NavController,
    childName: String
) {
    val segments = listOf(
        DonutSegment(color = Color(0xFF9B59FF), percent = 30f, label = "Kesehatan Mental"),
        DonutSegment(color = Color(0xFF2B6BE6), percent = 50f, label = "Kesehatan Umum"),
        DonutSegment(color = Color(0xFFF59E0B), percent = 15f, label = "Perubahan Fisik Lainnya"),
        DonutSegment(color = Color(0xFF10B981), percent = 5f, label = "Tinggi Badan")
    )

    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp)) {
        Text(
            text = "Grafik Perkembangan $childName",
            fontFamily = Sora,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        ProgressDonutChart(
            segments = segments,
            title = "Hasil Perkembangan $childName",
            subtitle = "Selama 2 Minggu Terakhir",
            onChartClick = { navController.navigate(com.example.piyo.presentation.navigation.InsightRoute) }
        )
    }
}

@Composable
private fun BelajarSection() {
    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp)) {
        Text(
            text = "Belajar Bersama Piyo",
            fontFamily = Sora,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = androidx.compose.ui.graphics.Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        BelajarBersamaPiyo(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ChatbotFab(navController: NavController, modifier: Modifier = Modifier) {
    androidx.compose.animation.AnimatedVisibility(
        visible = true,
        enter = androidx.compose.animation.scaleIn() + androidx.compose.animation.fadeIn(),
        exit = androidx.compose.animation.scaleOut() + androidx.compose.animation.fadeOut(),
        modifier = modifier
    ) {
        androidx.compose.material3.FloatingActionButton(
            onClick = { navController.navigate(ChatbotRoute) },
            containerColor = Color(0xFFFFC107),
            contentColor = Color.White
        ) {
            androidx.compose.material3.Icon(
                painter = androidx.compose.ui.res.painterResource(id = com.example.piyo.R.drawable.ic_chat),
                contentDescription = "Chat Bot",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun WelcomePopupWrapper(
    showWelcome: Boolean,
    userName: String,
    onDismiss: () -> Unit
) {
    WelcomePopup(
        visible = showWelcome,
        userName = userName,
        onDismiss = onDismiss
    )
}
