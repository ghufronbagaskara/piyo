package com.example.piyo.data

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.piyo.BuildConfig

object ChatbotService {
    private val model by lazy {
        GenerativeModel(
            modelName = "gemini-pro-latest",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }
}
