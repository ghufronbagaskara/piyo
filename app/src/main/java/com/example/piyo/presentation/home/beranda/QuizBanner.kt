package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piyo.R
import com.example.piyo.ui.theme.BlueMain
import com.example.piyo.ui.theme.YellowMain
import com.example.piyo.ui.theme.Sora
import androidx.compose.ui.graphics.Color

@Composable
fun QuizBanner(modifier: Modifier = Modifier, title: String = "Uji Pengetahuan tentang\nParenting Anda!\nMulai Kuis Sekarang", ctaText: String = "Mulai Kuis", illustrationRes: Int = R.drawable.ic_quiz_illustration, onCtaClick: () -> Unit = {}) {
    Card(modifier = modifier.fillMaxWidth().height(200.dp), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = BlueMain), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(0.62f).padding(24.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = title, fontFamily = Sora, fontSize = 16.sp, lineHeight = 20.sp, color = Color.White)
                Button(onClick = onCtaClick, colors = ButtonDefaults.buttonColors(containerColor = YellowMain), shape = RoundedCornerShape(20.dp), contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)) {
                    Text(text = ctaText, fontFamily = Sora, fontSize = 14.sp, color = Color.Black)
                }
            }

            Box(modifier = Modifier.weight(0.38f).fillMaxHeight().clip(RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp))) {
                Image(painter = painterResource(id = illustrationRes), contentDescription = "Ilustrasi Kuis", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                Box(modifier = Modifier.fillMaxSize().background(androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(Color(0x40000000), Color.Transparent)), shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp)))
            }
        }
    }
}
