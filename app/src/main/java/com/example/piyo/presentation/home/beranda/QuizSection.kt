package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement

@Composable
fun QuizSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 18.dp)) {
        Text(text = "Grafik Perkembangan Anak", fontSize = 16.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))

        Card(shape = RoundedCornerShape(7.dp), modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {
                // Donut chart
                Canvas(modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.TopCenter)) {
                    val stroke = size.minDimension / 6
                    val radius = size.minDimension / 2
                    val center = Offset(size.width / 2, size.height / 2)
                    // simple colored arcs
                    drawArc(color = Color(0xFFC084FC), startAngle = -90f, sweepAngle = 108f, useCenter = false, topLeft = Offset(center.x - radius, center.y - radius), size = androidx.compose.ui.geometry.Size(radius*2, radius*2), style = Stroke(width = stroke))
                    drawArc(color = Color(0xFF2563EB), startAngle = 18f, sweepAngle = 180f, useCenter = false, topLeft = Offset(center.x - radius, center.y - radius), size = androidx.compose.ui.geometry.Size(radius*2, radius*2), style = Stroke(width = stroke))
                    drawArc(color = Color(0xFFF59E0B), startAngle = 198f, sweepAngle = 54f, useCenter = false, topLeft = Offset(center.x - radius, center.y - radius), size = androidx.compose.ui.geometry.Size(radius*2, radius*2), style = Stroke(width = stroke))
                    drawArc(color = Color(0xFF22C55E), startAngle = 252f, sweepAngle = 18f, useCenter = false, topLeft = Offset(center.x - radius, center.y - radius), size = androidx.compose.ui.geometry.Size(radius*2, radius*2), style = Stroke(width = stroke))
                }

                Column(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    // legend items
                    LegendItem(color = Color(0xFFC084FC), label = "Kesehatan Mental", percent = "30%")
                    LegendItem(color = Color(0xFF2563EB), label = "Kesehatan Umum", percent = "50%")
                    LegendItem(color = Color(0xFFF59E0B), label = "Perubahan Fisik Lainnya", percent = "15%")
                    LegendItem(color = Color(0xFF22C55E), label = "Tinggi Badan", percent = "5%")
                }
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String, percent: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).background(color = color, shape = RoundedCornerShape(6.dp)))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontSize = 12.sp)
        }
        Text(text = percent, fontSize = 12.sp)
    }
}
