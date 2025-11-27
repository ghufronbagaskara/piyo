package com.example.piyo.presentation.home.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.piyo.R
import com.example.piyo.domain.model.EducationContent
import com.example.piyo.domain.model.QuizItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PiyoParentScreen(
    onNavigateToChatBot: () -> Unit,
    onNavigateToContentDetail: (String) -> Unit = {},
    onNavigateToQuizDetail: (String) -> Unit = {},
    viewModel: PiyoParentViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToChatBot,
                containerColor = Color(0xFFFFC107),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = "Chat Bot",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Search Bar
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Tab Row
            TabRow(
                selectedTabIndex = uiState.selectedTabIndex,
                containerColor = Color.White,
                contentColor = Color(0xFF0277BD)
            ) {
                Tab(
                    selected = uiState.selectedTabIndex == 0,
                    onClick = { viewModel.onTabSelected(0) },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_education),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = if (uiState.selectedTabIndex == 0) Color(0xFF0277BD) else Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Edukasi",
                                fontWeight = if (uiState.selectedTabIndex == 0) FontWeight.Bold else FontWeight.Normal,
                                color = if (uiState.selectedTabIndex == 0) Color(0xFF0277BD) else Color.Gray
                            )
                        }
                    }
                )
                Tab(
                    selected = uiState.selectedTabIndex == 1,
                    onClick = { viewModel.onTabSelected(1) },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_quiz),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = if (uiState.selectedTabIndex == 1) Color(0xFF0277BD) else Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Kuis",
                                fontWeight = if (uiState.selectedTabIndex == 1) FontWeight.Bold else FontWeight.Normal,
                                color = if (uiState.selectedTabIndex == 1) Color(0xFF0277BD) else Color.Gray
                            )
                        }
                    }
                )
            }

            // Content based on selected tab
            when (uiState.selectedTabIndex) {
                0 -> EducationContentList(
                    contents = uiState.educationContents,
                    isLoading = uiState.isEducationLoading,
                    error = uiState.educationError,
                    onItemClick = onNavigateToContentDetail
                )
                1 -> QuizList(
                    quizzes = uiState.quizzes,
                    isLoading = uiState.isQuizLoading,
                    error = uiState.quizError,
                    onItemClick = onNavigateToQuizDetail
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = {
            Text(
                text = "Cari",
                color = Color.Gray,
                fontSize = 16.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            disabledContainerColor = Color(0xFFF5F5F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun EducationContentList(
    contents: List<EducationContent>,
    isLoading: Boolean,
    error: String?,
    onItemClick: (String) -> Unit
) {
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF0277BD))
            }
        }
        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }
        contents.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada konten edukasi",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(contents) { content ->
                    EducationContentItem(
                        content = content,
                        onClick = { onItemClick(content.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun EducationContentItem(
    content: EducationContent,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        // Thumbnail Image
        AsyncImage(
            model = content.thumbnail,
            contentDescription = content.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder_education),
            error = painterResource(id = R.drawable.placeholder_education)
        )

        // Play icon for videos
        if (content.type == "video") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play_circle),
                    contentDescription = "Play",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        // Content info overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    color = Color.Black.copy(alpha = 0.3f)
                )
                .padding(16.dp)
        ) {
            // Type badge
            Box(
                modifier = Modifier
                    .background(
                        color = if (content.type == "video") Color(0xFFFFC107) else Color(0xFF0277BD),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (content.type == "video") "Video" else "Artikel",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = content.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
        }
    }
}

@Composable
fun QuizList(
    quizzes: List<QuizItem>,
    isLoading: Boolean,
    error: String?,
    onItemClick: (String) -> Unit
) {
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF0277BD))
            }
        }
        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }
        quizzes.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada kuis tersedia",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(quizzes) { quiz ->
                    QuizItem(
                        quiz = quiz,
                        onClick = { onItemClick(quiz.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuizItem(
    quiz: com.example.piyo.domain.model.QuizItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0277BD))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left side - Text content
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = quiz.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = quiz.description,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        maxLines = 2
                    )
                }

                // Mulai Kuis button
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC107),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = "Mulai Kuis",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            // Right side - Thumbnail
            AsyncImage(
                model = quiz.thumbnail,
                contentDescription = quiz.title,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder_quiz),
                error = painterResource(id = R.drawable.placeholder_quiz)
            )
        }
    }
}
