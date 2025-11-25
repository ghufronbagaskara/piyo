package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Close
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.piyo.presentation.navigation.ChatbotRoute
import com.example.piyo.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun BerandaScreen(navController: NavController) {
	Box(modifier = Modifier.fillMaxSize()) {
		// Scrollable content
		LazyColumn(modifier = Modifier.fillMaxSize()) {
			item { ProfileHeader(onNotificationClick = { navController.navigate(com.example.piyo.presentation.navigation.NotifikasiRoute) }) }

			item {
				Spacer(modifier = Modifier.height(12.dp))
				// Main content section (quiz banner)
				QuizBanner(modifier = Modifier.padding(horizontal = 20.dp), onCtaClick = {
					navController.navigate(com.example.piyo.presentation.navigation.QuizRoute)
				})
			}

			item { Spacer(modifier = Modifier.height(20.dp)) }

			item {
				Column(modifier = Modifier.padding(horizontal = 20.dp)) {
					Text(text = "Grafik Perkembangan Anak", style = MaterialTheme.typography.titleMedium)
					Spacer(modifier = Modifier.height(12.dp))

					Card(
						shape = RoundedCornerShape(12.dp),
						modifier = Modifier
							.fillMaxWidth()
							.wrapContentHeight()
					) {
						Box(
							modifier = Modifier
								.fillMaxWidth()
								.padding(16.dp),
							contentAlignment = Alignment.TopCenter
						) {
							// sample segments for the placeholder chart
							val segments = listOf(
								DonutSegment(color = Color(0xFF9B59FF), percent = 30f, label = "Kesehatan Mental"),
								DonutSegment(color = Color(0xFF2B6BE6), percent = 50f, label = "Kesehatan Umum"),
								DonutSegment(color = Color(0xFFF59E0B), percent = 15f, label = "Perubahan Fisik Lainnya"),
								DonutSegment(color = Color(0xFF10B981), percent = 5f, label = "Tinggi Badan")
							)

							// make the chart clickable to open Insight screen
							Box(modifier = Modifier
								.fillMaxWidth()
								.clickable { navController.navigate(com.example.piyo.presentation.navigation.InsightRoute) }
							) {
								ProgressDonutChart(
									modifier = Modifier.fillMaxWidth(),
									segments = segments,
									sizeDp = 220.dp,
									strokeWidth = 40.dp,
									title = "Hasil Perkembangan Anak Anda",
									subtitle = "Selama 2 Minggu Terakhir"
								)
							}
						}
					}
				}
			}

			// filler items to allow scrolling
			items((1..10).toList()) { _ ->
				Spacer(modifier = Modifier.height(20.dp))
			}
		}

		// Welcome popup: replace inlined AnimatedVisibility implementation with the reusable WelcomePopup
		var showWelcome by remember { mutableStateOf(true) }
		// Call the reusable WelcomePopup composable (it handles placement and styling)
		WelcomePopup(visible = showWelcome, onDismiss = { showWelcome = false }, modifier = Modifier.padding(start = 12.dp))

		// Floating yellow chatbot button bottom-end with robot vector
		AnimatedVisibility(
			visible = true,
			enter = scaleIn(animationSpec = spring(stiffness = 300f)) + fadeIn(),
			exit = scaleOut() + fadeOut(),
			// lower the FAB so it sits above the footer like the popup
			modifier = Modifier.align(Alignment.BottomEnd).padding(end = 16.dp, bottom = 12.dp)
		) {
			FloatingActionButton(
				onClick = { navController.navigate(ChatbotRoute) },
				containerColor = Color(0xFFFFC107),
				contentColor = Color.White,
				modifier = Modifier
					.size(64.dp)
					.clip(CircleShape)
			) {
				val painter = painterResource(id = R.drawable.ic_robot)
				Icon(painter = painter, contentDescription = "Chatbot", tint = Color.White, modifier = Modifier.size(28.dp))
			}
		}
	}
}


