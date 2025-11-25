package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
// StadiumShape not needed; using RoundedCornerShape instead
import androidx.compose.foundation.text.BasicTextField
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Composable header untuk beranda yang menampilkan foto profil, sapaan, nama pengguna,
 * tombol notifikasi, dan search bar seperti desain.
 */
@Composable
fun ProfileHeader(
	name: String = "Amalia Desafa!",
	modifier: Modifier = Modifier,
	onNotificationClick: () -> Unit = {},
	onSearch: (String) -> Unit = {}
) {
	Column(modifier = modifier) {
		val bgColor = Color(0xFFEBF5FF) // soft light-blue background similar to the design

		Surface(
			color = bgColor,
			shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
			modifier = Modifier
				.fillMaxWidth()
		) {
			Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
				Row(verticalAlignment = Alignment.CenterVertically) {
						// Avatar with blue ring and optional remote image (Coil)
						val avatarSize = 52.dp
						val avatarImageSize = 44.dp
						val avatarUrl = "https://picsum.photos/200" // replace with real URL or parameter
						Box(
							modifier = Modifier
								.size(avatarSize)
								.border(BorderStroke(2.dp, Color(0xFF1976D2)), CircleShape)
								.padding(4.dp)
								.clip(CircleShape)
								.background(Color.White),
							contentAlignment = Alignment.Center
						) {
							val context = LocalContext.current
							AsyncImage(
								model = ImageRequest.Builder(context)
									.data(avatarUrl)
									.crossfade(true)
									.build(),
								contentDescription = "Avatar",
								contentScale = ContentScale.Crop,
								modifier = Modifier
									.size(avatarImageSize)
									.clip(CircleShape),
								placeholder = null,
								error = null
							)
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
							style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
							color = Color.Black,
						)
					}

					// Notification icon inside a small circle with optional badge
					Box(modifier = Modifier.padding(start = 8.dp)) {
						IconButton(onClick = onNotificationClick) {
							Box(
								modifier = Modifier
									.size(40.dp)
									.clip(CircleShape)
									.background(Color(0xFFE6F2FF)),
								contentAlignment = Alignment.Center
							) {
								Icon(
									imageVector = Icons.Default.Notifications,
									contentDescription = "Notifikasi",
									tint = Color(0xFF1976D2),
									modifier = Modifier.size(20.dp)
								)
							}
						}

						// small notification dot
						Box(
							modifier = Modifier
								.size(10.dp)
								.background(Color(0xFFFFC107), CircleShape)
								.align(Alignment.TopEnd)
								.offset(x = 2.dp, y = (-2).dp)
						)
					}
				}

				Spacer(modifier = Modifier.height(16.dp))

				var query by remember { mutableStateOf("") }

				// Custom rounded search bar implemented with Surface + BasicTextField
				Surface(
					shape = RoundedCornerShape(24.dp),
					color = Color.White,
					tonalElevation = 0.dp,
					shadowElevation = 0.dp,
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp)
				) {
					Row(
						verticalAlignment = Alignment.CenterVertically,
						modifier = Modifier
							.padding(start = 12.dp, end = 12.dp)
							.fillMaxSize()
					) {
						Icon(
							imageVector = Icons.Default.Search,
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
								textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
								modifier = Modifier
									.fillMaxWidth()
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

@Preview(showBackground = true)
@Composable
private fun ProfileHeaderPreview() {
	MaterialTheme {
		ProfileHeader()
	}
}

