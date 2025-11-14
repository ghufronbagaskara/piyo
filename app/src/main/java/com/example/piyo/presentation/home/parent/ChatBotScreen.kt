package com.example.piyo.presentation.home.parent

import android.util.Log
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
import com.example.piyo.BuildConfig
import com.example.piyo.R
import com.example.piyo.ui.theme.BlueMain
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.*

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val isLoading: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotScreen(navController: NavController) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    var input by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var scrollJob by remember { mutableStateOf<Job?>(null) }

    val messages = remember {
        mutableStateListOf(
            ChatMessage("init_1", "Halo, kenalin namaku Piyo!", isUser = false),
            ChatMessage(
                "init_2",
                "Aku adalah asisten virtual di aplikasi Piyo, yang dirancang untuk membantu orang tua dalam mendukung anak-anak berkebutuhan khusus, terutama autisme.",
                isUser = false
            ),
            ChatMessage("init_3", "Bagaimana aku bisa membantu Anda hari ini?", isUser = false)
        )
    }

    var generativeModel by remember { mutableStateOf<GenerativeModel?>(null) }
    var modelStatus by remember { mutableStateOf("Menghubungkan...") }

    LaunchedEffect(Unit) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        Log.d("ChatBot", "Init model with key length: ${apiKey.length}")

        if (apiKey.length < 30) {
            modelStatus = "❌ API Key tidak valid"
            return@LaunchedEffect
        }

        val modelsToTry = listOf(
            "gemini-2.0-flash",
            "models/gemini-2.0-flash"
        )

        for (modelName in modelsToTry) {
            try {
                Log.d("ChatBot", "Trying model: $modelName")
                val testModel = GenerativeModel(
                    modelName = modelName,
                    apiKey = apiKey,
                    generationConfig = generationConfig {
                        temperature = 0.7f
                        maxOutputTokens = 500
                        topP = 0.8f
                    }
                )

                val testResponse = testModel.generateContent("test")
                if (testResponse.text != null) {
                    generativeModel = testModel
                    modelStatus = "✅ Online"
                    Log.d("ChatBot", "✓ Model $modelName works!")
                    break
                }
            } catch (e: Exception) {
                Log.e("ChatBot", "Model $modelName failed: ${e.message}")
            }
        }

        if (generativeModel == null) {
            modelStatus = "❌ Gagal terhubung"
        }
    }

    fun scrollToBottom() {
        scrollJob?.cancel()
        scrollJob = scope.launch {
            delay(50)
            if (messages.isNotEmpty()) {
                listState.scrollToItem(messages.size - 1)
            }
        }
    }

    fun sendMessage() {
        if (isProcessing || input.isBlank()) return
        val model = generativeModel ?: run {
            messages.add(
                ChatMessage(
                    "error_${System.currentTimeMillis()}",
                    "❌ Gemini API tidak tersedia. Periksa API Key.",
                    isUser = false
                )
            )
            scrollToBottom()
            return
        }

        val text = input.trim()
        isProcessing = true
        keyboardController?.hide()
        input = ""

        val userMsgId = "user_${System.currentTimeMillis()}"
        val loadingId = "loading_${System.currentTimeMillis()}"
        messages.add(ChatMessage(userMsgId, text, isUser = true))
        scrollToBottom()

        scope.launch {
            delay(100)
            messages.add(ChatMessage(loadingId, "...", isUser = false, isLoading = true))
            scrollToBottom()

            try {
                val prompt = """
                    Kamu adalah asisten virtual bernama Piyo.
                    Piyo membantu orang tua dengan anak berkebutuhan khusus (autisme).
                    Jawablah dengan empati, bahasa Indonesia yang lembut dan mudah dimengerti.
                    Hindari istilah medis rumit. Jawaban maksimal 4 kalimat.

                    Pertanyaan pengguna: "$text"
                """.trimIndent()

                val response = withContext(Dispatchers.IO) {
                    model.generateContent(prompt)
                }

                val reply = response.text?.trim() ?: throw Exception("Empty response")

                val loadingIndex = messages.indexOfFirst { it.id == loadingId }
                if (loadingIndex != -1) messages.removeAt(loadingIndex)
                delay(50)

                messages.add(ChatMessage("bot_${System.currentTimeMillis()}", reply, isUser = false))
            } catch (e: Exception) {
                val loadingIndex = messages.indexOfFirst { it.id == loadingId }
                if (loadingIndex != -1) messages.removeAt(loadingIndex)
                delay(50)
                messages.add(
                    ChatMessage(
                        "error_${System.currentTimeMillis()}",
                        "❌ ${e.message ?: "Terjadi kesalahan"}",
                        isUser = false
                    )
                )
            } finally {
                isProcessing = false
                scrollToBottom()
            }
        }
    }

    LaunchedEffect(messages.size) {
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
                            text = modelStatus,
                            fontSize = 12.sp,
                            color = when {
                                modelStatus.contains("Online", true) -> Color(0xFF10B981)
                                modelStatus.contains("Menghubungkan", true) -> Color(0xFFF59E0B)
                                else -> Color(0xFFEF4444)
                            }
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
                userScrollEnabled = !isProcessing
            ) {
                item(key = "day_chip") {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        DayChip("Hari ini")
                    }
                }

                items(messages, key = { it.id }) { msg ->
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
                        enabled = !isProcessing,
                        maxLines = 4,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = { if (input.trim().isNotEmpty()) sendMessage() }
                        ),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFDADADA),
                            unfocusedBorderColor = Color(0xFFE6E6E6),
                            disabledBorderColor = Color(0xFFE6E6E6),
                            focusedContainerColor = Color(0xFFF8F8F8),
                            unfocusedContainerColor = Color(0xFFF8F8F8),
                            disabledContainerColor = Color(0xFFF0F0F0),
                            cursorColor = BlueMain
                        )
                    )

                    IconButton(
                        onClick = { sendMessage() },
                        enabled = input.trim().isNotEmpty() && !isProcessing,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                if (input.trim().isNotEmpty() && !isProcessing)
                                    BlueMain else Color(0xFFE0E0E0),
                                shape = CircleShape
                            )
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
