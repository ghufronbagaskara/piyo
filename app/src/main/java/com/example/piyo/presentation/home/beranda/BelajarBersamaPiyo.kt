package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.piyo.ui.theme.Sora
import com.example.piyo.R

data class LearningResource(val id: Int, val title: String, val isVideo: Boolean = false, val thumbnailRes: Int = R.drawable.ic_child_placeholder)

@Composable
fun BelajarBersamaPiyo(modifier: Modifier = Modifier, resources: List<LearningResource> = sampleResources(), onItemClick: (LearningResource) -> Unit = {}) {
    Column(modifier = modifier) {
        Text(text = "Belajar Bersama Piyo", fontFamily = Sora, fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp))
        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(horizontal = 12.dp)) {
            items(resources) { resource ->
                ResourceCard(resource = resource, onClick = { onItemClick(resource) })
            }
        }
    }
}

@Composable
private fun ResourceCard(resource: LearningResource, onClick: () -> Unit) {
    Card(modifier = Modifier.width(260.dp).aspectRatio(1.8f).clickable(onClick = onClick), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(painter = painterResource(id = resource.thumbnailRes), contentDescription = resource.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().background(Color.Black.copy(alpha = 0.45f)).padding(12.dp)) {
                Text(text = resource.title, color = Color.White, fontFamily = Sora, fontSize = 14.sp)
            }
            Box(modifier = Modifier.align(Alignment.TopStart).padding(8.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF1976D2)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(text = if (resource.isVideo) "Video" else "Artikel", color = Color.White, fontFamily = Sora, fontSize = 12.sp)
            }
        }
    }
}

private fun sampleResources(): List<LearningResource> = listOf(
    LearningResource(1, "Tips Parenting untuk Orang Tua Anak dengan Autisme", false, R.drawable.ic_quiz_illustration),
    LearningResource(2, "Membangun Lingkungan yang Mendukung untuk Anak Autisme", true, R.drawable.ic_child_placeholder),
    LearningResource(3, "Strategi Komunikasi Efektif", false, R.drawable.ic_child_placeholder),
    LearningResource(4, "Kegiatan Sensori yang Menyenangkan", false, R.drawable.ic_child_placeholder)
)
