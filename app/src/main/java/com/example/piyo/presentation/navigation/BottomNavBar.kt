package com.example.piyo.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.piyo.R
import com.example.piyo.ui.theme.BlueMain
import com.example.piyo.ui.theme.YellowMain

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    val navItems = listOf(
        NavItem("Beranda", R.drawable.ic_home),
        NavItem("Piyo Parent", R.drawable.ic_parent),
        NavItem("Piyo Plan", R.drawable.ic_plan),
        NavItem("Pengaturan", R.drawable.ic_settings)
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = modifier
    ) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemClick(index) },
                icon = {
                    if (selectedItem == index) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(BlueMain),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            tint = Color.Gray
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selectedItem == index) BlueMain else Color.Gray
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

data class NavItem(
    val title: String,
    val icon: Int
)
