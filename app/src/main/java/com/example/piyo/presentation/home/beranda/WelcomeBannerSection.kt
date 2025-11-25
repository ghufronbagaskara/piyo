package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeBannerSection(modifier: Modifier = Modifier) {
    // Simple bottom navigation bar approximation
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(81.dp)
            .background(Color.White, shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .padding(horizontal = 22.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavItem(label = "Beranda", active = true)
        NavItem(label = "Piyo Parent", active = false)
        NavItem(label = "Piyo Plan", active = false)
        NavItem(label = "Pengaturan", active = false)
    }
}

@Composable
private fun NavItem(label: String, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // placeholder for icon
        Box(modifier = Modifier.size(24.dp).background(if (active) Color(0xFF0386DE) else Color(0xFFCCCCCC), shape = RoundedCornerShape(6.dp)))
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, fontSize = 13.sp, color = if (active) Color(0xFF0386DE) else Color(0xFFBBBBBB))
    }
}
