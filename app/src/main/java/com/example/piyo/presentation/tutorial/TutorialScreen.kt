package com.example.piyo.presentation.tutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {

            Box(
                Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < 2) {
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
                        text = if (pagerState.currentPage < 2) "Selanjutnya" else "Selesai",
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

            Image(
                painter = painterResource(id = R.drawable.ellipse_169),
                contentDescription = null,
                modifier = Modifier
                    .size(420.dp)
                    .offset(x = (-180).dp, y = (-180).dp)
                    .align(Alignment.TopStart),
                alpha = 0.35f
            )
            Image(
                painter = painterResource(id = R.drawable.ellipse_170),
                contentDescription = null,
                modifier = Modifier
                    .size(420.dp)
                    .offset(x = (-40).dp, y = (540).dp)
                    .align(Alignment.BottomCenter),
                alpha = 0.5f
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Top
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (pagerState.currentPage > 0) {
                        IconButton(onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(48.dp))
                    }
                    Text(
                        text = "Lewati",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { navController.navigate(LoginRoute) }
                    )
                }

                ProgressIndicatorBar(
                    currentPage = pagerState.currentPage,
                    totalPages = 3
                )


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    HorizontalPager(state = pagerState) { page ->
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
                .fillMaxWidth(0.9f)
                .aspectRatio(0.85f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = title, color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = desc,
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun ProgressIndicatorBar(currentPage: Int, totalPages: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(5.dp)
                    .padding(horizontal = 3.dp)
                    .background(
                        if (index <= currentPage) BlueMain else Color(0xFFD9E5EE),
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}
