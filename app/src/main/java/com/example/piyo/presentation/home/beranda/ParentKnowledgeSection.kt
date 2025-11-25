package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment

private data class LearningCard(
    val id: Int,
    val type: String,
    val title: String,
    val gradientStart: Color,
    val gradientEnd: Color,
    val hasPlayButton: Boolean = false
)

@Composable
fun ParentKnowledgeSection(modifier: Modifier = Modifier) {
    val cards = listOf(
        LearningCard(
            1,
            "Artikel",
            "Tips Parenting untuk Orang Tua Anak dengan Autisme",
            Color.Transparent,
            Color(0xFFA67D00),
            false
        ),
        LearningCard(
            2,
            "Video",
            "Membangun Lingkungan yang Mendukung untuk Anak Autisme",
            Color.Transparent,
            Color(0xFF0386DE),
            true
        ),
        LearningCard(
            3,
            "Artikel",
            "Membangun Lingkungan yang Mendukung untuk Anak Autisme",
            Color.Transparent,
            Color(0xFFA67D00),
            false
        )
    )

    Column(modifier = modifier.fillMaxWidth().padding(start = 16.dp, top = 12.dp)) {
        Text(text = "Belajar Bersama Piyo", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cards) { card ->
                Card(
                    modifier = Modifier
                        .width(329.dp)
                        .height(163.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(colors = listOf(card.gradientStart, card.gradientEnd))
                            )
                            .padding(12.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                            Box {
                                // Badge
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFF0386DE), shape = RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Text(text = card.type, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Text(text = card.title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        if (card.hasPlayButton) {
                            Box(modifier = Modifier
                                .size(66.dp)
                                .align(Alignment.CenterEnd)
                                .offset(x = (-12).dp)
                                .background(Color(0x80FFFFFF), shape = RoundedCornerShape(34.dp))) {
                                // play icon placeholder
                            }
                        }
                    }
                }
            }
        }
    }
}
