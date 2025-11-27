package com.example.piyo.presentation.home.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.R
import com.example.piyo.presentation.navigation.KeamananIzinRoute
import com.example.piyo.ui.theme.BlueMain
import com.example.piyo.ui.theme.YellowMain
import com.example.piyo.util.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    onLogout: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val displayName = currentUser?.displayName ?: "Pengguna"
    val email = currentUser?.email ?: "Tidak diketahui"
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: navigate to ChatBot */ },
                containerColor = YellowMain,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Outlined.SmartToy, contentDescription = "Ask Piyo")
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Pengaturan",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(Modifier.height(16.dp))
            ProfileHeader(name = displayName, email = email)
            Spacer(Modifier.height(28.dp))

            SettingItem(
                icon = { LeadingIcon(icon = Icons.Outlined.Person) },
                title = "Edit Profil",
                subtitle = "Lakukan perubahan pada akun Anda",
                onClick = { /* TODO */ }
            )

            SettingItem(
                icon = { LeadingIcon(icon = Icons.Outlined.Security) },
                title = "Keamanan & Izin",
                subtitle = "Amankan akun Anda demi keamanan",
                onClick = { navController.navigate(KeamananIzinRoute) }
            )

            SettingItem(
                icon = { LeadingIcon(icon = Icons.Outlined.Lock) },
                title = "Informasi Anak",
                subtitle = "Lihat data anak Anda",
                onClick = { /* TODO */ }
            )

            SettingItem(
                icon = { LeadingIcon(icon = Icons.Outlined.HelpOutline) },
                title = "Pusat Bantuan",
                subtitle = "Dapatkan bantuan dan dukungan",
                onClick = { /* TODO */ }
            )

            Divider(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = Color(0xFFF0F0F0)
            )

            SettingItem(
                icon = { LeadingIcon(icon = Icons.Outlined.Logout, color = Color(0xFFE53935)) },
                title = "Keluar",
                subtitle = "Keluar dari akun Anda",
                textColor = Color(0xFFE53935),
                onClick = {
                    coroutineScope.launch {
                        try {
                            FirebaseUtils.logout()
                            snackbarHostState.showSnackbar("Anda telah keluar dari akun")
                            onLogout()
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Gagal keluar: ${e.message}")
                        }
                    }
                }
            )

            Spacer(Modifier.height(120.dp))
        }
    }
}

@Composable
private fun ProfileHeader(name: String, email: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            color = BlueMain,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(text = email, color = Color(0xFFEAF2FB), fontSize = 14.sp)
            }
        }

        Box(
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .background(Color.White)
                .border(3.dp, BlueMain, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_child_placeholder),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
private fun SettingItem(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(2.dp))
            Text(text = subtitle, color = Color(0xFF8A8A8E), fontSize = 13.sp)
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color(0xFF9BA3AF))
    }
}

@Composable
private fun LeadingIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color = BlueMain
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
    }
}
