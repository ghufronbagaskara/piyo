package com.example.piyo.presentation.tutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.R
import com.example.piyo.presentation.navigation.LoginRoute
import com.example.piyo.ui.theme.BlueMain
import com.example.piyo.ui.theme.YellowMain
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialScreen(navController: NavController) {
    val totalPages = 3
    val pagerState = rememberPagerState(pageCount = { totalPages })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 20.dp)
                    .navigationBarsPadding()
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < totalPages - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                navController.navigate(LoginRoute)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = YellowMain),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = if (pagerState.currentPage < totalPages - 1) "Selanjutnya" else "Selesai",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {

            // Background decorations
            Image(
                painter = painterResource(id = R.drawable.ellipse_169),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .offset(x = (-200).dp, y = (-200).dp)
                    .align(Alignment.TopStart),
                alpha = 0.3f
            )
            Image(
                painter = painterResource(id = R.drawable.ellipse_170),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .offset(x = (-50).dp, y = (550).dp)
                    .align(Alignment.BottomCenter),
                alpha = 0.4f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top section with navigation and progress
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Back button or spacer
                        if (pagerState.currentPage > 0) {
                            IconButton(onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.Black
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.width(48.dp))
                        }

                        // Skip button
                        Text(
                            text = "Lewati",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable {
                                navController.navigate(LoginRoute)
                            }
                        )
                    }

                    // Progress indicator
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(totalPages) { index ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .background(
                                        color = if (index <= pagerState.currentPage) BlueMain else Color(0xFFD9E5EE),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                    }

                    // Page counter
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "${pagerState.currentPage + 1}/$totalPages",
                            color = BlueMain,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Content pager
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        TutorialPage(page)
                    }
                }
            }
        }
    }
}

@Composable
fun TutorialPage(page: Int) {
    val (imageRes, title, desc) = when (page) {
        0 -> Triple(
            R.drawable.ic_tutorial_parent,
            "PiyoParent",
            "Yuk, belajar parenting dengan modul edukasi dan kuis interaktif! Dapatkan tips yang sesuai untuk anak autisme, dan cek progres belajarmu kapan saja."
        )
        1 -> Triple(
            R.drawable.ic_tutorial_askpiyo,
            "AskPiyo",
            "Tanyakan langsung ke chatbot pintar! Dapatkan solusi cepat dan dukungan emosional kapan pun dibutuhkan."
        )
        else -> Triple(
            R.drawable.ic_tutorial_plan,
            "PiyoPlan",
            "Atur jadwal dan kegiatan terapi anakmu dengan mudah! Sinkronkan dengan kalender dan aktifkan pengingat otomatis."
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(0.85f)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(text = title, color = Color.Black, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = desc,
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
