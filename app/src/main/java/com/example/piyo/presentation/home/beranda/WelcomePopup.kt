package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Text
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

@Composable
fun WelcomePopup(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!visible) return

    Popup(
        alignment = Alignment.BottomStart,
        properties = PopupProperties(focusable = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentAlignment = Alignment.BottomStart
        ) {
            Card(
                modifier = Modifier
                    .width(332.dp)
                    .padding(start = 12.dp, bottom = 86.dp)
                    .then(modifier),
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp, bottomEnd = 7.dp, bottomStart = 7.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF1FF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(modifier = Modifier.padding(12.dp)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                            Text(
                                text = "Selamat datang di Piyo!",
                                fontFamily = Sora,
                                fontSize = 16.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Aplikasi untuk mendukung perkembangan anak dengan autism. Mari mulai!",
                            fontFamily = Sora,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            OutlinedButton(
                                onClick = onDismiss,
                                shape = RoundedCornerShape(20.dp),
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                            ) {
                                Text(text = "Selanjutnya", color = BlueMain, fontFamily = Sora)
                            }
                        }
                    }
                }
            }
        }
    }
}
