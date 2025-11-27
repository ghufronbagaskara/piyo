package com.example.piyo.seed

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object EducationSeeder {

    private val firestore = FirebaseFirestore.getInstance()

    fun seed() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                seedEducationContents()
                Log.d("SEED", "Seeding completed successfully!")
            } catch (e: Exception) {
                Log.e("SEED", "Error seeding data: ${e.message}")
            }
        }
    }

    private suspend fun seedEducationContents() {
        val now = Timestamp.now()

        val data = listOf(
            // VIDEO 1
            mapOf(
                "type" to "video",
                "title" to "Memahami Autisme: Panduan Dasar untuk Orang Tua",
                "thumbnail" to "https://img.youtube.com/vi/4s-9la4gn_g/maxresdefault.jpg",
                "description" to "Penjelasan dasar mengenai autisme, karakteristik, dan cara mendukung anak.",
                "duration" to "12:11",
                "createdAt" to now,
                "tags" to listOf("autisme", "parenting", "basic"),
                "videoUrl" to "https://www.youtube.com/watch?v=4s-9la4gn_g"
            ),

            // VIDEO 2
            mapOf(
                "type" to "video",
                "title" to "Cara Berkomunikasi yang Baik dengan Anak Autis",
                "thumbnail" to "https://img.youtube.com/vi/8yL0uqukZiY/maxresdefault.jpg",
                "description" to "Teknik komunikasi sederhana untuk membantu anak autisme memahami instruksi.",
                "duration" to "08:45",
                "createdAt" to now,
                "tags" to listOf("autisme", "communication", "skills"),
                "videoUrl" to "https://www.youtube.com/watch?v=8yL0uqukZiY"
            ),

            // VIDEO 3
            mapOf(
                "type" to "video",
                "title" to "Sensory Processing & Autism: Memahami Sensitivitas Anak",
                "thumbnail" to "https://img.youtube.com/vi/7_FvK5FQ3UE/maxresdefault.jpg",
                "description" to "Penjelasan tentang sensory issues pada anak autisme dan cara mengelolanya.",
                "duration" to "10:02",
                "createdAt" to now,
                "tags" to listOf("autisme", "sensory", "education"),
                "videoUrl" to "https://www.youtube.com/watch?v=7_FvK5FQ3UE"
            ),

            // VIDEO 4
            mapOf(
                "type" to "video",
                "title" to "Strategi Perilaku di Rumah untuk Anak dengan Autisme",
                "thumbnail" to "https://img.youtube.com/vi/x67KJJkYjYc/maxresdefault.jpg",
                "description" to "Cara memberikan dukungan perilaku harian agar anak lebih tenang dan fokus.",
                "duration" to "09:15",
                "createdAt" to now,
                "tags" to listOf("autisme", "behavior", "tips"),
                "videoUrl" to "https://www.youtube.com/watch?v=x67KJJkYjYc"
            ),

            // VIDEO 5
            mapOf(
                "type" to "video",
                "title" to "Tips Menghadapi Sekolah untuk Anak Autisme",
                "thumbnail" to "https://img.youtube.com/vi/ahz3uGQKq9g/maxresdefault.jpg",
                "description" to "Strategi agar anak autisme dapat beradaptasi lebih baik di lingkungan sekolah.",
                "duration" to "11:30",
                "createdAt" to now,
                "tags" to listOf("autisme", "school", "guidance"),
                "videoUrl" to "https://www.youtube.com/watch?v=ahz3uGQKq9g"
            ),

            // ARTICLE 1
            mapOf(
                "type" to "article",
                "title" to "Apa Itu Autism Spectrum Disorder? Panduan untuk Orang Tua",
                "thumbnail" to "https://i.ibb.co/vY1TzqZ/autism-basic.png",
                "description" to "Penjelasan ringkas mengenai ASD, tanda-tanda awal, dan dukungan yang tepat.",
                "duration" to "6 min read",
                "createdAt" to now,
                "tags" to listOf("autisme", "ASD", "parenting")
            ),

            // ARTICLE 2
            mapOf(
                "type" to "article",
                "title" to "Cara Membangun Rutinitas yang Efektif untuk Anak Autis",
                "thumbnail" to "https://i.ibb.co/1QhfYQF/autism-routine.png",
                "description" to "Rutinitas membantu anak autisme merasa aman dan stabil. Tips praktis untuk orang tua.",
                "duration" to "5 min read",
                "createdAt" to now,
                "tags" to listOf("autisme", "routine", "daily life")
            ),

            // ARTICLE 3
            mapOf(
                "type" to "article",
                "title" to "Mengatasi Tantrum pada Anak Autisme",
                "thumbnail" to "https://i.ibb.co/6wZQwSh/autism-tantrum.png",
                "description" to "Penyebab tantrum dan teknik efektif untuk menenangkannya di rumah.",
                "duration" to "7 min read",
                "createdAt" to now,
                "tags" to listOf("autisme", "behavior", "tantrum")
            ),

            // ARTICLE 4
            mapOf(
                "type" to "article",
                "title" to "Strategi Komunikasi Alternatif untuk Anak Nonverbal",
                "thumbnail" to "https://i.ibb.co/87MyQGp/autism-nonverbal.png",
                "description" to "Cara menggunakan gambar, gesture, atau teknologi untuk komunikasi anak nonverbal.",
                "duration" to "6 min read",
                "createdAt" to now,
                "tags" to listOf("autisme", "communication", "nonverbal")
            ),

            // ARTICLE 5
            mapOf(
                "type" to "article",
                "title" to "Cara Mengembangkan Kemandirian Anak Autisme",
                "thumbnail" to "https://i.ibb.co/smT123Z/autism-independence.png",
                "description" to "Tips melatih kemandirian anak autisme dalam aktivitas sehari-hari.",
                "duration" to "8 min read",
                "createdAt" to now,
                "tags" to listOf("autisme", "skills", "independence")
            )
        )

        data.forEach { item ->
            firestore.collection("education_contents")
                .add(item)
                .await()
        }
    }
}
