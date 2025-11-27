// home/insight/InsightScreen.kt
package com.example.piyo.presentation.home.insight

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.presentation.home.insight.model.DonutSegment
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun InsightScreen(navController: NavController) {

    val segments = listOf(
        DonutSegment(Color(0xFF9B59FF), 30f, "Kesehatan Mental"),
        DonutSegment(Color(0xFF2B6BE6), 50f, "Kesehatan Umum"),
        DonutSegment(Color(0xFFF59E0B), 15f, "Perubahan Fisik Lainnya"),
        DonutSegment(Color(0xFF10B981), 5f, "Tinggi Badan")
    )

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top app bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("Insight", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {

                // Week insight card (white card with thin border & small elevation)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFEEEEF1))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Minggu ke-02", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Horizontal stacked bar
                        HealthBarChart(segments = segments, modifier = Modifier.fillMaxWidth())

                        Spacer(modifier = Modifier.height(12.dp))

                        // Legend rows below the bar (no heavy background)
                        segments.forEach { seg ->
                            LegendRow(seg.label, seg.color, "${seg.percent.toInt()}%")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                DetailCard(
                    title = "Detail Perkembangan Fisik",
                    description = "Pantau tinggi badan, berat badan, dan perubahan postur anak Anda dari waktu ke waktu.",
                    details = listOf(
                        "Tinggi Badan (cm)" to "94 cm",
                        "Berat Badan" to "19,7 Kg",
                        "Perubahan Postur" to "Postur tubuh lebih tegap"
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                DetailCard(
                    title = "Kondisi Kesehatan",
                    description = "Pantau riwayat kesehatan anak Anda, catat perubahan signifikan dalam kesehatan anak.",
                    details = listOf("Catatan Kesehatan" to "Tambah Catatan Kesehatan"),
                    footerText = "September 2024 - Mengalami peningkatan dalam kemampuan berkomunikasi setelah terapi wicara selama 6 bulan."
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DonutChart(
    segments: List<DonutSegment>,
    size: Dp,
    strokeWidth: Dp,
    onSegmentClick: (String) -> Unit
) {
    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }
    val pxSize = with(LocalDensity.current) { size.toPx() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val center = pxSize / 2f
                    val x = offset.x - center
                    val y = offset.y - center

                    val angle = ((atan2(y, x) * 180 / PI) + 360 + 90) % 360

                    var cumulative = 0f
                    for (seg in segments) {
                        val sweep = seg.percent / 100f * 360f
                        if (angle >= cumulative && angle < cumulative + sweep) {
                            onSegmentClick(seg.label)
                            break
                        }
                        cumulative += sweep
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val total = 100f
            val radius = pxSize / 2f - strokePx / 2f
            var startAngle = -90f

            segments.forEach { seg ->
                val sweep = (seg.percent / total) * 360f
                drawArc(
                    color = seg.color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(width = strokePx, cap = StrokeCap.Butt)
                )
                startAngle += sweep
            }
        }
    }
}

@Composable
private fun LegendRow(label: String, color: Color, percent: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(color, RoundedCornerShape(7.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, modifier = Modifier.weight(1f))
        Text(percent, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun HealthBarChart(segments: List<com.example.piyo.presentation.home.insight.model.DonutSegment>, modifier: Modifier = Modifier) {
    // Draw a horizontal stacked bar where each segment's width is proportional to its percent
    val total = segments.sumOf { it.percent.toDouble() }.toFloat().coerceAtLeast(1f)

    Row(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        segments.forEach { seg ->
            // weight relative to total so proportions are correct
            Box(
                modifier = Modifier
                    .weight(seg.percent / total)
                    .fillMaxHeight()
                    .background(seg.color)
            ) {}
        }
    }
}


@Composable
private fun DetailCard(
    title: String,
    description: String,
    details: List<Pair<String, String>>,
    footerText: String? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFEEEEF1))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(description, color = Color(0xFF6B6B6B), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))
            details.forEach { (t, v) -> DetailRow(t, v) }
            footerText?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun DetailRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFEFF6FF),
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {}
        }

        Spacer(modifier = Modifier.width(12.dp))
        Text(title, color = Color(0xFF1976D2), modifier = Modifier.weight(1f))
        Text(value)
    }
}
