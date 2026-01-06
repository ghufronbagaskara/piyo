package com.example.piyo.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.piyo.BuildConfig
import com.example.piyo.domain.model.Child

object ChatbotService {
    private val model by lazy {
        GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = BuildConfig.GEMINI_API_KEY,
            generationConfig = generationConfig {
                temperature = 0.7f
                maxOutputTokens = 500
                topP = 0.8f
            }
        )
    }

    /**
     * Generate response with context about user's children
     */
    suspend fun generateResponse(
        userMessage: String,
        children: List<Child> = emptyList()
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val childrenContext = buildChildrenContext(children)

            val prompt = """
                Kamu adalah asisten virtual bernama Piyo.
                Piyo membantu orang tua dengan anak berkebutuhan khusus (autisme).
                Jawablah dengan empati, bahasa Indonesia yang lembut dan mudah dimengerti.
                Hindari istilah medis rumit. Jawaban maksimal 4 kalimat.
                
                ${if (childrenContext.isNotEmpty()) "Informasi anak pengguna:\n$childrenContext\n" else ""}
                Pertanyaan pengguna: "$userMessage"
            """.trimIndent()

            val response = model.generateContent(prompt)
            val reply = response.text?.trim()
                ?: throw Exception("Empty response from AI")

            Result.success(reply)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Build context string from children data
     */
    private fun buildChildrenContext(children: List<Child>): String {
        if (children.isEmpty()) return ""

        return children.joinToString("\n\n") { child ->
            buildString {
                append("- Nama: ${child.fullName}")
                if (child.birthDate.isNotEmpty()) {
                    append("\n  Tanggal Lahir: ${child.birthDate}")
                    append("\n  Usia: ${calculateAge(child.birthDate)}")
                }
                if (child.gender.isNotEmpty()) {
                    append("\n  Jenis Kelamin: ${child.gender}")
                }
                if (child.birthWeight > 0) {
                    append("\n  Berat Lahir: ${child.birthWeight} kg")
                }
                if (child.diagnosisHistory.isNotEmpty()) {
                    append("\n  Riwayat Diagnosis: ${child.diagnosisHistory}")
                }
            }
        }
    }

    /**
     * Calculate age from birth date (format: dd/MM/yyyy)
     */
    private fun calculateAge(birthDate: String): String {
        return try {
            val parts = birthDate.split("/")
            if (parts.size != 3) return "tidak diketahui"

            val day = parts[0].toInt()
            val month = parts[1].toInt()
            val year = parts[2].toInt()

            val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
            val currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1
            val currentDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)

            var age = currentYear - year
            if (currentMonth < month || (currentMonth == month && currentDay < day)) {
                age--
            }

            "$age tahun"
        } catch (e: Exception) {
            "tidak diketahui"
        }
    }
}
