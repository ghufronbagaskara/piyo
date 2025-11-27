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
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {

            Box(
                Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
                    .padding(horizontal = 24.dp, vertical = 20.dp)
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

                // Top row: Back | Progress indicator (center) | Lewati
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                        Spacer(modifier = Modifier.size(48.dp))
                    }

                    // Center progress indicator
                    Box(modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ProgressIndicatorWithThumb(
                            currentPage = pagerState.currentPage,
                            totalPages = totalPages,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        )
                    }

                    Text(
                        text = "Lewati",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clickable { navController.navigate(LoginRoute) }
                            .padding(start = 8.dp)
                    )
                }

                // small page count on the right under the top bar (matching sample image style)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "${pagerState.currentPage + 1}/$totalPages",
                        color = BlueMain,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 8.dp),
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

@Composable
fun ProgressIndicatorWithThumb(currentPage: Int, totalPages: Int, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.height(40.dp)) {
        val fullWidthPx = constraints.maxWidth
        val fullWidthDp = with(LocalDensity.current) { fullWidthPx.toDp() }
        val thumbSize = 18.dp
        val segmentSpacing = 8.dp

        // segments row
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(6.dp),
            horizontalArrangement = Arrangement.spacedBy(segmentSpacing)
        ) {
            repeat(totalPages) { index ->
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = if (index <= currentPage) BlueMain else Color(0xFFD9E5EE),
                        shape = RoundedCornerShape(50)
                    )
                )
            }
        }

        // thumb overlay
        if (totalPages > 1) {
            // compute x offset in dp where thumb center should be
            val availableWidthDp = fullWidthDp - segmentSpacing * (totalPages - 1)
            val segmentWidthDp = availableWidthDp / totalPages
            val centerOffsetDp = segmentWidthDp * (currentPage + 0.5f) + segmentSpacing * currentPage
            val thumbOffsetDp = centerOffsetDp - (thumbSize / 2f)

            Box(
                modifier = Modifier
                    .offset(x = thumbOffsetDp)
                    .size(thumbSize)
                    .background(color = BlueMain, shape = CircleShape)
                    .align(Alignment.CenterStart)
            )
        }
    }
}
