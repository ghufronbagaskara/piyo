package com.example.piyo.presentation.home.insight

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.presentation.home.beranda.DonutSegment

@Composable
fun InsightScreen(navController: NavController) {
    // sample data; in real app this would be passed or loaded from VM
    val segments = listOf(
        DonutSegment(color = Color(0xFF9B59FF), percent = 30f, label = "Kesehatan Mental"),
        DonutSegment(color = Color(0xFF2B6BE6), percent = 50f, label = "Kesehatan Umum"),
        DonutSegment(color = Color(0xFFF59E0B), percent = 15f, label = "Perubahan Fisik Lainnya"),
        DonutSegment(color = Color(0xFF10B981), percent = 5f, label = "Tinggi Badan")
    )

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top app bar with back arrow and title centered
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(text = "Insight", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.weight(1f))
                // placeholder to keep title centered
                Box(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Scrollable content
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)) {

                // Card: top section with week and bar + legend
                Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)) {
                        Text(text = "Minggu ke-", fontSize = 14.sp, color = Color(0xFF6B6B6B))
                        Text(text = "02", fontSize = 40.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))

                        // simple horizontal stacked bar representation
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(Color(0xFFF3F4F6), shape = RoundedCornerShape(4.dp))) {
                            // proportional boxes approximating the segments
                            Box(modifier = Modifier
                                .weight(0.3f)
                                .fillMaxHeight()
                                .background(color = segments[0].color))
                            Box(modifier = Modifier
                                .weight(0.5f)
                                .fillMaxHeight()
                                .background(color = segments[1].color))
                            Box(modifier = Modifier
                                .weight(0.15f)
                                .fillMaxHeight()
                                .background(color = segments[2].color))
                            Box(modifier = Modifier
                                .weight(0.05f)
                                .fillMaxHeight()
                                .background(color = segments[3].color))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Legend rows with right aligned percent
                        Column(modifier = Modifier.fillMaxWidth()) {
                            segments.forEach { seg ->
                                LegendRow(label = seg.label, color = seg.color, percent = "${seg.percent.toInt()}%")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Detail card: Perkembangan Fisik
                Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Detail Perkembangan Fisik", fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Pantau tinggi badan, berat badan, dan perubahan postur anak Anda dari waktu ke waktu.", color = Color(0xFF6B6B6B), fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        // three detail rows
                        DetailRow(title = "Tinggi Badan (cm)", value = "94 cm")
                        DetailRow(title = "Berat Badan", value = "19,7 Kg")
                        DetailRow(title = "Perubahan Postur", value = "Postur tubuh lebih tegap")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Health condition card
                Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Kondisi Kesehatan", fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Pantau riwayat kesehatan anak Anda, catat perubahan signifikan dalam kesehatan anak, seperti gejala baru atau perubahan dalam kondisi yang ada.", color = Color(0xFF6B6B6B), fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Catatan Kesehatan", color = Color(0xFF1976D2), fontWeight = FontWeight.SemiBold)

                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFF3F4F6), modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Tambah Catatan Kesehatan", modifier = Modifier.padding(12.dp), color = Color(0xFF9CA3AF))
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "September 2024 - Mengalami peningkatan dalam kemampuan berkomunikasi setelah mengikuti terapi wicara selama 6 bulan.\n\nDokter mencatat peningkatan positif dalam interaksi sosial dan komunikasi. Disarankan untuk melanjutkan terapi wicara dan aktivitas sensorik.", fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun LegendRow(label: String, color: Color, percent: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(14.dp).background(color = color, shape = RoundedCornerShape(7.dp)))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, modifier = Modifier.weight(1f))
        Text(text = percent, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun DetailRow(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        // icon placeholder
        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFEFF6FF), modifier = Modifier.size(40.dp)) {
            Box(contentAlignment = Alignment.Center) { /* icon could go here */ }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, color = Color(0xFF1976D2), modifier = Modifier.weight(1f))
        Text(text = value)
    }
}
