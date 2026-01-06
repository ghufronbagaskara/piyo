package com.example.piyo.presentation.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.piyo.R
import com.example.piyo.domain.model.EducationContent
import com.example.piyo.ui.theme.BlueMain
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultScreen(
    navController: NavController,
    viewModel: QuizViewModel = koinViewModel()
) {
    val state = viewModel.state
    val result = state.quizResult

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        // Navigate back to PiyoParent when back button pressed
                        navController.navigate(com.example.piyo.presentation.navigation.PiyoParentRoute) {
                            popUpTo(com.example.piyo.presentation.navigation.PiyoParentRoute) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Illustration
            Image(
                painter = painterResource(id = R.drawable.ic_child_placeholder),
                contentDescription = "Success Illustration",
                modifier = Modifier
                    .size(180.dp)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Result card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selamat!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Anda telah menyelesaikan kuis",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Untuk feedback dan saran, silakan lihat rekomendasi di bawah ini untuk mendapatkan tips yang berguna untuk parenting anak autism.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Jawaban benar Anda :",
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${result?.correctAnswers ?: 0}/${result?.totalQuestions ?: 5}",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Recommendations section
            if (state.recommendedContents.isNotEmpty()) {
                Text(
                    text = "Rekomendasi Artikel",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.recommendedContents) { content ->
                        RecommendedContentCard(
                            content = content,
                            onClick = {
                                // Navigate to education content detail
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // Navigate back to PiyoParent screen (Kuis tab)
                    navController.navigate(com.example.piyo.presentation.navigation.PiyoParentRoute) {
                        // Clear all quiz-related screens from back stack
                        popUpTo(com.example.piyo.presentation.navigation.PiyoParentRoute) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text(
                    text = "Selesai",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun RecommendedContentCard(
    content: EducationContent,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = content.thumbnail,
                contentDescription = content.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_child_placeholder)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                // Type badge
                Surface(
                    color = BlueMain,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = content.type.replaceFirstChar { it.uppercase() },
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = content.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2
                )
            }
        }
    }
}
