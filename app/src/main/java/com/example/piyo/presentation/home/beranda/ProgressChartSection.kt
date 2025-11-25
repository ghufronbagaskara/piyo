package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset

@Composable
fun ProgressChartSection(modifier: Modifier = Modifier, onNext: () -> Unit = {}) {
    Column(modifier = modifier.padding(18.dp)) {
        Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(142.dp)
                .padding(12.dp)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(text = "Selamat datang di Piyo!", fontSize = 14.sp, color = Color(0xFF1C1C1B))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Aplikasi untuk mendukung perkembangan anak dengan autism. Mari mulai!", fontSize = 12.sp, color = Color(0xFF4B4B4B))
                }

                // Donut chart placeholder on right
                Canvas(modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterEnd)) {
                    val stroke = size.minDimension / 5
                    val radius = size.minDimension / 2
                    val center = Offset(size.width/2, size.height/2)
                    // draw background circle
                    drawCircle(color = Color(0xFFEDEDED), radius = radius, center = center, style = Stroke(width = stroke))
                    // draw segments (demo distribution)
                    drawArcSegment(this, center, radius, stroke, 0f, 108f, Color(0xFFC084FC)) // 30%
                    drawArcSegment(this, center, radius, stroke, 108f, 288f, Color(0xFF2563EB)) // 50%
                    drawArcSegment(this, center, radius, stroke, 288f, 342f, Color(0xFFF59E0B)) // 15%
                    drawArcSegment(this, center, radius, stroke, 342f, 360f, Color(0xFF22C55E)) // 5%
                }
            }
        }
    }
}

private fun drawArcSegment(drawScope: DrawScope, center: Offset, radius: Float, stroke: Float, startDeg: Float, endDeg: Float, color: Color) {
    with(drawScope) {
        drawArc(
            color = color,
            startAngle = startDeg,
            sweepAngle = endDeg - startDeg,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
            style = Stroke(width = stroke)
        )
    }
}
