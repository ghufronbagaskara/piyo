package com.example.piyo.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.ui.theme.BlueMain
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizQuestionScreen(
    navController: NavController,
    childAge: Int,
    childId: String,
    viewModel: QuizViewModel = koinViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadQuiz(childAge, childId)
        viewModel.startQuiz()
    }

    // Navigate to result when quiz is completed
    LaunchedEffect(state.isQuizCompleted) {
        if (state.isQuizCompleted) {
            navController.navigate(com.example.piyo.presentation.navigation.QuizResultRoute) {
                popUpTo(navController.graph.id) {
                    inclusive = false
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = BlueMain)
        }
        return
    }

    if (state.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(state.error, color = Color.Red)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Kembali")
                }
            }
        }
        return
    }

    val currentQuestion = viewModel.getCurrentQuestion()

    Scaffold(
        containerColor = Color(0xFFFFC107),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFC107))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Timer,
                            contentDescription = "Timer",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = viewModel.formatTime(state.timeRemaining),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress bar
                LinearProgressIndicator(
                    progress = viewModel.getProgress(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = BlueMain,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Pertanyaan ke ${state.currentQuestionIndex + 1} dari ${state.quiz?.questions?.size ?: 5}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFC107))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            currentQuestion?.let { question ->
                Text(
                    text = question.question,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                question.options.forEachIndexed { index, option ->
                    QuizOptionButton(
                        text = "${('a' + index)}) $option",
                        isSelected = state.selectedAnswerIndex == index,
                        isCorrect = state.isAnswered && index == question.correctAnswerIndex,
                        isWrong = state.isAnswered && state.selectedAnswerIndex == index && !state.isCorrect,
                        enabled = !state.isAnswered,
                        onClick = { viewModel.selectAnswer(index) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (state.isAnswered) {
                Button(
                    onClick = { viewModel.nextQuestion() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BlueMain)
                ) {
                    Text(
                        text = if (state.currentQuestionIndex < (state.quiz?.questions?.size ?: 5) - 1) "Selanjutnya" else "Lihat Hasil",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizOptionButton(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isWrong: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isCorrect -> Color(0xFF4CAF50)
        isWrong -> Color(0xFFF44336)
        else -> Color(0xFFFFF8E1)
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor
        ),
        enabled = enabled
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = if (isCorrect || isWrong) Color.White else Color.Black,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
