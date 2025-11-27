package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.piyo.ui.theme.Sora
import com.example.piyo.ui.theme.BlueMain
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.font.FontWeight

@Composable
fun WelcomePopup(visible: Boolean, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    if (!visible) return
    Popup(alignment = Alignment.BottomStart, properties = PopupProperties(focusable = true)) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
            Card(
                modifier = Modifier
                    .width(340.dp)
                    .padding(start = 16.dp, bottom = 86.dp)
                    .then(modifier),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 10.dp, bottomStart = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF1FF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                        Text(
                            text = "Selamat datang di Piyo!",
                            fontFamily = Sora,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = BlueMain)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Aplikasi untuk mendukung perkembangan anak dengan autism. Mari mulai!",
                        fontFamily = Sora,
                        fontSize = 14.sp,
                        color = Color(0xFF374151)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedButton(
                            onClick = onDismiss,
                            border = BorderStroke(1.dp, BlueMain),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                        ) {
                            Text(text = "Selanjutnya", color = BlueMain, fontFamily = Sora)
                        }
                    }
                }
            }
        }
    }
}
