package com.example.piyo.presentation.home.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.R
import com.example.piyo.ui.theme.Sora

data class NotificationItemModel(
    val id: Int,
    val title: String,
    val message: String,
    val time: String,
    val iconRes: Int? = R.drawable.ic_child_placeholder
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotifikasiScreen(navController: NavController) {
    // Sample data (mirror of the anima export). You can replace with real data later.
    val items = remember {
        mutableStateListOf(
            NotificationItemModel(1, "Hai, Amalia!", "Ingatkan dirimu untuk mengunggah foto perkembangan anak hari ini. Setiap langkah penting!", "18 menit", R.drawable.ic_child_placeholder),
            NotificationItemModel(2, "Peringatan!", "Waktunya menyelesaikan kuis parenting dan lihat progres belajarmu!", "5 jam", R.drawable.ic_child_placeholder),
            NotificationItemModel(3, "Ingat, Amalia!", "Ayo, tanya apa saja yang ingin kamu ketahui di AskPiyo hari ini!", "2 hari", R.drawable.ic_child_placeholder),
            NotificationItemModel(4, "Jangan lupa!", "Saatnya memeriksa jadwal terapi anakmu. Siapkan semua yang dibutuhkan!", "2 hari", R.drawable.ic_child_placeholder)
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Notifikasi", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.ic_home), contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color(0xFFFFFFFF)
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
        ) {
            items(items, key = { it.id }) { item ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Icon in rounded square background (light blue)
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFB4D0FF), shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = item.iconRes ?: R.drawable.ic_child_placeholder),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Title (bold) + message (regular) as a single Text so wrapping lines align
                        Column(modifier = Modifier.weight(1f)) {
                            val annotated = androidx.compose.ui.text.buildAnnotatedString {
                                pushStyle(androidx.compose.ui.text.SpanStyle(fontFamily = Sora, fontWeight = FontWeight.SemiBold, fontSize = 13.sp))
                                append(item.title)
                                pop()
                                append(" ")
                                pushStyle(androidx.compose.ui.text.SpanStyle(fontFamily = Sora, fontWeight = FontWeight.Normal, fontSize = 13.sp))
                                append(item.message)
                                pop()
                            }

                            Text(
                                text = annotated,
                                fontSize = 13.sp,
                                color = Color(0xFF1E1E20)
                            )
                        }

                        // Time aligned top-right
                        Text(
                            text = item.time,
                            color = Color(0xFF9B9B9B),
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }
            }
        }
    }
}
