package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piyo.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MainContentSection(modifier: Modifier = Modifier, onStartQuiz: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Page title
        val soraHeader = FontFamily(Font(R.font.sora_bold))
        Text(
            text = "Coba kuis sekarang",
            fontSize = 28.sp,
            fontFamily = soraHeader,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 0.dp)
        )

        // Banner card (Figma 1:1 target)
        Box(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .clip(RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                // draw inner stroke using drawBehind so the border appears inset like the design
                val borderColor = Color(0xFF2563EB)
                val strokeDp = 3.dp // 3dp inset stroke to match Figma ~3px
                val cornerDp = 20.dp
                val strokePx = with(LocalDensity.current) { strokeDp.toPx() }
                val cornerPx = with(LocalDensity.current) { cornerDp.toPx() }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawBehind {
                            // inset the rect by half stroke so stroke is fully inside
                            val left = strokePx / 2f
                            val top = strokePx / 2f
                            val width = size.width - strokePx
                            val height = size.height - strokePx
                            drawRoundRect(
                                color = borderColor,
                                topLeft = Offset(left, top),
                                size = Size(width, height),
                                cornerRadius = CornerRadius(cornerPx, cornerPx),
                                style = Stroke(width = strokePx)
                            )
                        }
                        .background(
                            Brush.horizontalGradient(
                                // Figma stops: left bright blue -> mid blue -> right light gray
                                colorStops = arrayOf(
                                    0.0f to Color(0xFF2563EB),
                                    0.6f to Color(0xFF1D4ED8),
                                    1.0f to Color(0xFFD1D5DB)
                                )
                            )
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    // Font family Sora
                    val sora = FontFamily(
                        Font(R.font.sora_regular),
                        Font(R.font.sora_medium, FontWeight.Medium),
                        Font(R.font.sora_semibold, FontWeight.SemiBold),
                        Font(R.font.sora_bold, FontWeight.Bold),
                        Font(R.font.sora_extrabold, FontWeight.ExtraBold)
                    )

                    Row(modifier = Modifier.fillMaxSize()) {
                        // Left (60%) - text + CTA
                        Box(
                            modifier = Modifier
                                .weight(0.6f)
                                .fillMaxHeight()
                                .padding(start = 20.dp, top = 18.dp, bottom = 16.dp, end = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Uji Pengetahuan tentang\nParenting Anda!",
                                    color = Color.White,
                                    fontSize = 44.sp,
                                    fontFamily = sora,
                                    fontWeight = FontWeight.ExtraBold,
                                    lineHeight = 48.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Text(
                                    text = "Mulai Kuis Sekarang",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontFamily = sora,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 32.sp,
                                    modifier = Modifier.padding(top = 6.dp)
                                )

                                ElevatedButton(
                                    onClick = onStartQuiz,
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                        .height(40.dp)
                                        .wrapContentWidth()
                                        .padding(horizontal = 4.dp),
                                    shape = RoundedCornerShape(40),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFBBF24),
                                        contentColor = Color.Black
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                                ) {
                                    Text(
                                        text = "Mulai Kuis",
                                        fontFamily = sora,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 14.dp)
                                    )
                                }
                            }
                        }

                        // Right (40%) - illustration clipped to card's rounded corners
                        Box(
                            modifier = Modifier
                                .weight(0.4f)
                                .fillMaxHeight()
                                .padding(end = 0.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_quiz_illustration),
                                contentDescription = "quiz illustration",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}
