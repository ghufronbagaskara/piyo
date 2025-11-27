package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.piyo.ui.theme.Sora
import androidx.navigation.NavController
import com.example.piyo.presentation.navigation.ChatbotRoute

@Composable
fun BerandaScreen(navController: NavController) {
    var showWelcome by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { HeaderSection(navController) }
            item { QuizTitleSection() }
            item { QuizSection(navController) }
            item { InsightSection(navController) }
            item { BelajarSection() }

            // filler agar scroll lebih nyaman
            items((1..5).toList()) {
                Spacer(Modifier.height(20.dp))
            }
        }

        WelcomePopupWrapper(showWelcome = showWelcome, onDismiss = { showWelcome = false })

        ChatbotFab(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 12.dp)
        )
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
private fun QuizSection(navController: NavController) {
    QuizBanner(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        onCtaClick = { navController.navigate("QuizRoute") }
    )
}

@Composable
private fun InsightSection(navController: NavController) {
    val segments = listOf(
        DonutSegment(color = androidx.compose.ui.graphics.Color(0xFF9B59FF), percent = 30f, label = "Kesehatan Mental"),
        DonutSegment(color = androidx.compose.ui.graphics.Color(0xFF2B6BE6), percent = 50f, label = "Kesehatan Umum"),
        DonutSegment(color = androidx.compose.ui.graphics.Color(0xFFF59E0B), percent = 15f, label = "Perubahan Fisik Lainnya"),
        DonutSegment(color = androidx.compose.ui.graphics.Color(0xFF10B981), percent = 5f, label = "Tinggi Badan")
    )

    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp)) {
        Text(
            text = "Grafik Perkembangan Anak",
            fontFamily = Sora,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = androidx.compose.ui.graphics.Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

            ProgressDonutChart(
                segments = segments,
                title = "Hasil Perkembangan Anak Anda",
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
            containerColor = androidx.compose.ui.graphics.Color(0xFFFFC107),
            contentColor = androidx.compose.ui.graphics.Color.White
        ) {
            androidx.compose.material3.Icon(
                painter = androidx.compose.ui.res.painterResource(id = com.example.piyo.R.drawable.ic_robot),
                contentDescription = "Chatbot"
            )
        }
    }
}

@Composable
private fun WelcomePopupWrapper(showWelcome: Boolean, onDismiss: () -> Unit) {
    WelcomePopup(visible = showWelcome, onDismiss = onDismiss)
}
