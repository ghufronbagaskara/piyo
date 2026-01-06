package com.example.piyo.presentation.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.R
import com.example.piyo.ui.theme.BlueMain

@Composable
fun QuizIntroductionScreen(
    navController: NavController,
    childAge: Int,
    childId: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Illustration placeholder
        Image(
            painter = painterResource(id = R.drawable.ic_child_placeholder),
            contentDescription = "Quiz Illustration",
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Selamat Datang di Kuis Parenting Anak!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sebelum kita mulai, ada beberapa hal yang perlu diperhatikan:",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InstructionItem(
                    number = 1,
                    text = "Kuis ini terdiri dari 5 pertanyaan dan akan memakan waktu sekitar 15 menit."
                )
            }
            item {
                InstructionItem(
                    number = 2,
                    text = "Durasi sekitar 15 menit"
                )
            }
            item {
                InstructionItem(
                    number = 3,
                    text = "Pilih jawaban yang paling sesuai dengan pengalaman Anda."
                )
            }
            item {
                InstructionItem(
                    number = 4,
                    text = "Semua jawaban Anda akan dijaga kerahasiaannya dan hanya digunakan untuk tujuan edukatif."
                )
            }
            item {
                InstructionItem(
                    number = 5,
                    text = "Setelah menyelesaikan kuis, Anda akan mendapatkan feedback yang dapat membantu Anda dalam perjalanan parenting Anda."
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate(
                    com.example.piyo.presentation.navigation.QuizQuestionRoute(
                        childAge = childAge,
                        childId = childId
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
        ) {
            Text(
                text = "Mulai Kuis Sekarang!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun InstructionItem(number: Int, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$number.",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = BlueMain,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xFF424242),
            modifier = Modifier.weight(1f)
        )
    }
}
