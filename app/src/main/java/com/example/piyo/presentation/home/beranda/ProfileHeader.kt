package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.filled.Search
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piyo.R
import com.example.piyo.ui.theme.Sora

@Composable
fun ProfileHeader(
    name: String = "Pengguna!",
    photoUrl: String = "",
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        val bgColor = Color(0xFFEBF5FF)
        Surface(
            color = bgColor,
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val avatarSize = 52.dp
                    val avatarImageSize = 44.dp

                    Box(
                        modifier = Modifier
                            .size(avatarSize)
                            .border(2.dp, Color(0xFF1976D2), CircleShape)
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        val context = LocalContext.current
                        if (photoUrl.isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(photoUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(avatarImageSize)
                                    .clip(CircleShape)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_child_placeholder),
                                contentDescription = "Default Avatar",
                                modifier = Modifier.size(avatarImageSize),
                                tint = Color(0xFF1976D2)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Halo",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6B6B6B)
                        )
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold
                            ),
                            color = Color.Black,
                            maxLines = 1
                        )
                    }

                    IconButton(onClick = onNotificationClick) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE6F2FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifikasi",
                                tint = Color(0xFF1976D2)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                var query by remember { mutableStateOf("") }
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxSize()
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Cari",
                            tint = Color(0xFF6B6B6B)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(modifier = Modifier.fillMaxWidth()) {
                            BasicTextField(
                                value = query,
                                onValueChange = {
                                    query = it
                                    onSearch(it)
                                },
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (query.isEmpty()) {
                                Text(
                                    text = "Cari",
                                    color = Color(0xFF8A8A8A),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}