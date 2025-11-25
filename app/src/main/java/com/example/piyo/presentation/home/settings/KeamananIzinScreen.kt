package com.example.piyo.presentation.home.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piyo.ui.theme.Sora
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

@Composable
private fun SettingRow(
    title: String,
    subtitle: String? = null,
    trailing: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = Sora,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            if (!subtitle.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = Color(0xFFA6A39F)
                )
            }
        }

        if (trailing != null) {
            Box(modifier = Modifier.padding(start = 8.dp).align(Alignment.CenterVertically)) {
                trailing()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeamananIzinScreen(navController: NavController) {
    var saveLoginInfo by remember { mutableStateOf(false) }
    var allowPermission by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFF0F1F1), // page background from exported CSS
        topBar = {
            // Centered top app bar with transparent background so title won't be shifted by nav icon
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Keamanan & Izin",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 13.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Keamanan Section label
            Text(
                text = "Keamanan",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 6.dp)
            )

            // White rounded card like design
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 367.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
            ) {
                Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 15.dp)) {
                    // Keamanan rows are inserted here (SettingRow calls moved to top-level)
                    SettingRow(
                        title = "Ganti kata sandi",
                        trailing = {
                            IconButton(onClick = { /* Navigate to change password */ }) {
                                Icon(
                                    imageVector = Icons.Filled.ChevronRight,
                                    contentDescription = null,
                                    tint = Color(0xFF0386DE) // primary-base from tokens
                                )
                            }
                        }
                    )

                    HorizontalDivider(color = Color(0xFFF0F0F0), thickness = 1.dp)

                    SettingRow(
                        title = "Verifikasi 2 langkah",
                        trailing = {
                            // Make this chevron match the Ganti kata sandi chevron (same tint/size)
                            IconButton(onClick = { /* Navigate to 2FA */ }) {
                                Icon(
                                    imageVector = Icons.Filled.ChevronRight,
                                    contentDescription = null,
                                    tint = Color(0xFF0386DE) // primary-base
                                )
                            }
                        }
                    )

                    HorizontalDivider(color = Color(0xFFF0F0F0), thickness = 1.dp)

                    SettingRow(
                        title = "Simpan info masuk",
                        trailing = {
                            Switch(
                                modifier = Modifier.size(width = 46.dp, height = 23.dp),
                                checked = saveLoginInfo,
                                onCheckedChange = { saveLoginInfo = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                    uncheckedThumbColor = Color(0xFFFFFFFF),
                                    uncheckedTrackColor = Color(0xFFCECDCD)
                                )
                            )
                        }
                    )
                }
            }

            // Izin Section label
            Text(
                text = "Izin",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 6.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 367.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
            ) {
                Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 15.dp)) {
                    // Card contains only the short title and trailing switch
                    SettingRow(
                        title = "Izinkan Piyo untuk meminta izin menggunakan",
                        trailing = {
                            Switch(
                                modifier = Modifier.size(width = 46.dp, height = 23.dp),
                                checked = allowPermission,
                                onCheckedChange = { allowPermission = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                    uncheckedThumbColor = Color(0xFFFFFFFF),
                                    uncheckedTrackColor = Color(0xFFCECDCD)
                                )
                            )
                        }
                    )
                }
            }

            // Long explanatory text should be placed below the card (outside)
            Text(
                text = "Izinkan Piyo untuk meminta menggunakan Suara Personal untuk mengucapkan sesuatu dengan lantang melalui speaker perangkat Anda",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = Color(0xFFA6A39F),
                modifier = Modifier.padding(horizontal = 18.dp)
            )
        }
    }
}
