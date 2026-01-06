package com.example.piyo.presentation.home.parent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piyo.R
import com.example.piyo.ui.theme.BlueMain
import kotlinx.coroutines.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotScreen(
    navController: NavController,
    viewModel: ChatBotViewModel = koinViewModel()
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val uiState by viewModel.uiState.collectAsState()
    var input by remember { mutableStateOf("") }
    var scrollJob by remember { mutableStateOf<Job?>(null) }

    fun scrollToBottom() {
        scrollJob?.cancel()
        scrollJob = scope.launch {
            delay(50)
            if (uiState.messages.isNotEmpty()) {
                listState.scrollToItem(uiState.messages.size - 1)
            }
        }
    }

    fun sendMessage() {
        if (uiState.isProcessing || input.isBlank()) return

        val text = input.trim()
        keyboardController?.hide()
        input = ""

        viewModel.sendMessage(text)
    }

    LaunchedEffect(uiState.messages.size) {
        delay(100)
        scrollToBottom()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color(0xFF1F1F1F)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.ic_piyo_logo),
                        contentDescription = "Piyo",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Piyo Assistant",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1F1F1F)
                        )
                        Text(
                            text = if (uiState.isLoadingChildren) {
                                "Memuat data anak..."
                            } else if (uiState.children.isNotEmpty()) {
                                "✅ Terhubung dengan ${uiState.children.size} anak"
                            } else {
                                "✅ Online"
                            },
                            fontSize = 12.sp,
                            color = Color(0xFF10B981)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = {
                        navController.navigate(com.example.piyo.presentation.navigation.NotifikasiRoute)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifikasi",
                            tint = Color(0xFF1F1F1F)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                userScrollEnabled = !uiState.isProcessing
            ) {
                item(key = "day_chip") {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        DayChip("Hari ini")
                    }
                }

                items(uiState.messages, key = { it.id }) { msg ->
                    when {
                        msg.isUser -> UserBubble(msg.text)
                        msg.isLoading -> BotTypingBubble()
                        else -> BotBubble(msg.text)
                    }
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .imePadding(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        placeholder = {
                            Text("Tulis pesan...", color = Color(0xFF9E9E9E), fontSize = 14.sp)
                        },
                        enabled = !uiState.isProcessing,
                        maxLines = 4,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = { sendMessage() }
                        ),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BlueMain,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            disabledBorderColor = Color(0xFFE0E0E0),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color(0xFFF5F5F5)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp, max = 120.dp)
                    )

                    Button(
                        onClick = { sendMessage() },
                        enabled = input.isNotBlank() && !uiState.isProcessing,
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BlueMain,
                            disabledContainerColor = Color(0xFFE0E0E0)
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Send,
                            contentDescription = "Kirim",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DayChip(text: String) {
    Surface(color = Color(0xFFF1F4F8), shape = RoundedCornerShape(16.dp)) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = Color(0xFF6B7280),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun BotBubble(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(0.85f),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_piyo_logo),
            contentDescription = "Piyo",
            modifier = Modifier.size(28.dp).clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFE7E7E9))
        ) {
            Text(
                text = text,
                color = Color(0xFF1F1F1F),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun BotTypingBubble() {
    Row(
        modifier = Modifier.fillMaxWidth(0.85f),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_piyo_logo),
            contentDescription = "Piyo",
            modifier = Modifier.size(28.dp).clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Surface(
            color = Color(0xFFF8F8F8),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Piyo sedang mengetik...",
                color = Color(0xFF9E9E9E),
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun UserBubble(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            color = BlueMain,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            )
        }
    }
}
