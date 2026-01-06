package com.example.piyo.seed

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

object ComprehensiveSeeder {

    private val firestore = FirebaseFirestore.getInstance()
    private const val PARENT_ID = "ONVn46KKQdXXe51bHdAvXwNg8Q63"
    private const val CHILD_ID = "jRi6kzOFNySnSepHT7sg"

    fun seed() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("SEED", "Starting comprehensive seeding...")

                seedChildData()
                seedPlanTasks()
                seedQuizzes()
                seedQuizContents()

                Log.d("SEED", "✅ All seeding completed successfully!")
            } catch (e: Exception) {
                Log.e("SEED", "❌ Error seeding data: ${e.message}", e)
            }
        }
    }

    private suspend fun seedChildData() {
        try {
            val now = Timestamp.now()

            val childData = mapOf(
                "parentId" to PARENT_ID,
                "fullName" to "Budi Santoso",
                "birthDate" to "2020-05-15",
                "gender" to "male",
                "birthWeight" to 3.2,
                "diagnosisHistory" to "Autism Spectrum Disorder (ASD) - Level 1, didiagnosis pada usia 3 tahun",
                "profilePhotoUrl" to "",
                "babyPhotoUrl" to "",
                "createdAt" to now,
                "updatedAt" to now
            )

            // Update existing child document
            firestore.collection("children")
                .document(CHILD_ID)
                .set(childData)
                .await()

            Log.d("SEED", "✅ Child data seeded successfully")
        } catch (e: Exception) {
            Log.e("SEED", "Error seeding child data: ${e.message}", e)
        }
    }

    private suspend fun seedPlanTasks() {
        try {
            val now = Timestamp.now()
            val calendar = Calendar.getInstance()

            val tasks = listOf(
                // Hari ini
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Terapi Okupasi",
                    "description" to "Sesi terapi okupasi dengan Bu Sari di klinik",
                    "date" to getTodayDate(),
                    "time" to "09:00",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Minum Vitamin",
                    "description" to "Berikan vitamin omega-3 setelah makan siang",
                    "date" to getTodayDate(),
                    "time" to "12:30",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Sesi Bermain Sosial",
                    "description" to "Playdate dengan teman sekolah di taman",
                    "date" to getTodayDate(),
                    "time" to "15:00",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),

                // Besok
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Terapi Wicara",
                    "description" to "Sesi terapi wicara dengan Bu Ani",
                    "date" to getTomorrowDate(),
                    "time" to "10:00",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Kelas Seni",
                    "description" to "Kelas melukis dan menggambar",
                    "date" to getTomorrowDate(),
                    "time" to "14:00",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),

                // Lusa
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Kontrol Dokter",
                    "description" to "Konsultasi rutin dengan dr. Bambang, SpA",
                    "date" to getDateAfterDays(2),
                    "time" to "11:00",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Berenang",
                    "description" to "Kelas berenang untuk sensory therapy",
                    "date" to getDateAfterDays(2),
                    "time" to "16:00",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),

                // Minggu depan
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Terapi ABA",
                    "description" to "Applied Behavior Analysis session",
                    "date" to getDateAfterDays(7),
                    "time" to "09:30",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Kelas Musik",
                    "description" to "Terapi musik dengan alat musik sederhana",
                    "date" to getDateAfterDays(8),
                    "time" to "13:00",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                ),
                mapOf(
                    "parentId" to PARENT_ID,
                    "childId" to CHILD_ID,
                    "title" to "Kunjungan ke Taman",
                    "description" to "Outdoor activity untuk sensory stimulation",
                    "date" to getDateAfterDays(9),
                    "time" to "10:00",
                    "isCompleted" to false,
                    "createdAt" to now,
                    "updatedAt" to now
                )
            )

            val collection = firestore.collection("plan_tasks")
            tasks.forEach { task ->
                collection.add(task).await()
            }

            Log.d("SEED", "✅ Plan tasks seeded: ${tasks.size} tasks")
        } catch (e: Exception) {
            Log.e("SEED", "Error seeding plan tasks: ${e.message}", e)
        }
    }

    private suspend fun seedQuizzes() {
        try {
            val now = Timestamp.now()

            val quizzes = listOf(
                mapOf(
                    "title" to "Dasar-Dasar Parenting Anak Autis",
                    "thumbnail" to "https://i.ibb.co/vY1TzqZ/autism-basic.png",
                    "description" to "Uji pemahaman Anda tentang cara mendidik dan mendampingi anak dengan autism spectrum disorder",
                    "totalQuestions" to 10,
                    "duration" to 15,
                    "difficulty" to "easy",
                    "createdBy" to "Tim Piyo",
                    "createdAt" to now,
                    "tags" to listOf("parenting", "autism", "basic", "education")
                ),
                mapOf(
                    "title" to "Komunikasi Efektif dengan Anak Autis",
                    "thumbnail" to "https://i.ibb.co/Lh8Y9vK/communication.png",
                    "description" to "Pelajari teknik komunikasi yang tepat untuk membangun hubungan yang lebih baik dengan anak autis",
                    "totalQuestions" to 8,
                    "duration" to 12,
                    "difficulty" to "medium",
                    "createdBy" to "Tim Piyo",
                    "createdAt" to now,
                    "tags" to listOf("communication", "autism", "skills", "parenting")
                ),
                mapOf(
                    "title" to "Mengelola Perilaku Tantrum",
                    "thumbnail" to "https://i.ibb.co/hCT8WYv/behavior.png",
                    "description" to "Strategi praktis menghadapi tantrum dan meltdown pada anak dengan autisme",
                    "totalQuestions" to 10,
                    "duration" to 15,
                    "difficulty" to "medium",
                    "createdBy" to "Tim Piyo",
                    "createdAt" to now,
                    "tags" to listOf("behavior", "tantrum", "autism", "strategies")
                ),
                mapOf(
                    "title" to "Sensory Processing pada Anak Autis",
                    "thumbnail" to "https://i.ibb.co/9w4L8Jq/sensory.png",
                    "description" to "Memahami sensitivitas sensorik dan cara mengelola sensory overload",
                    "totalQuestions" to 9,
                    "duration" to 12,
                    "difficulty" to "hard",
                    "createdBy" to "Tim Piyo",
                    "createdAt" to now,
                    "tags" to listOf("sensory", "autism", "advanced", "therapy")
                ),
                mapOf(
                    "title" to "Rutinitas Harian untuk Anak Autis",
                    "thumbnail" to "https://i.ibb.co/QkX6ZHJ/routine.png",
                    "description" to "Cara membuat dan menjalankan rutinitas yang konsisten dan efektif",
                    "totalQuestions" to 7,
                    "duration" to 10,
                    "difficulty" to "easy",
                    "createdBy" to "Tim Piyo",
                    "createdAt" to now,
                    "tags" to listOf("routine", "daily", "autism", "structure")
                ),
                mapOf(
                    "title" to "Mendukung Perkembangan Sosial Anak",
                    "thumbnail" to "https://i.ibb.co/pKT9Fvr/social.png",
                    "description" to "Tips membantu anak autis mengembangkan keterampilan sosial dan pertemanan",
                    "totalQuestions" to 8,
                    "duration" to 12,
                    "difficulty" to "medium",
                    "createdBy" to "Tim Piyo",
                    "createdAt" to now,
                    "tags" to listOf("social", "friendship", "autism", "development")
                )
            )

            val collection = firestore.collection("quizzes")
            quizzes.forEach { quiz ->
                collection.add(quiz).await()
            }

            Log.d("SEED", "✅ Quizzes seeded: ${quizzes.size} quizzes")
        } catch (e: Exception) {
            Log.e("SEED", "Error seeding quizzes: ${e.message}", e)
        }
    }

    private suspend fun seedQuizContents() {
        try {
            val quizContents = listOf(
                // Quiz untuk usia 2-4 tahun
                mapOf(
                    "ageSegment" to "2-4",
                    "title" to "Kuis Parenting Anak Autis (2-4 tahun)",
                    "duration" to 10,
                    "questions" to listOf(
                        mapOf(
                            "aspect" to "Komunikasi",
                            "question" to "Bagaimana cara terbaik mengajarkan anak autis usia 2-4 tahun untuk berkomunikasi?",
                            "options" to listOf(
                                "Menggunakan banyak kata-kata rumit",
                                "Menggunakan gambar, gestur, dan kata-kata sederhana",
                                "Membiarkan anak belajar sendiri",
                                "Hanya menggunakan bahasa verbal"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Anak autis usia dini lebih mudah memahami komunikasi visual dan sederhana. Kombinasi gambar, gestur, dan kata-kata sederhana membantu mereka belajar berkomunikasi dengan lebih efektif."
                        ),
                        mapOf(
                            "aspect" to "Rutinitas",
                            "question" to "Mengapa rutinitas harian penting untuk anak autis usia 2-4 tahun?",
                            "options" to listOf(
                                "Agar orang tua tidak repot",
                                "Memberikan rasa aman dan prediktabilitas",
                                "Tidak terlalu penting",
                                "Hanya untuk disiplin"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Rutinitas memberikan struktur dan prediktabilitas yang membuat anak autis merasa aman. Ini mengurangi kecemasan dan membantu mereka memahami apa yang akan terjadi selanjutnya."
                        ),
                        mapOf(
                            "aspect" to "Bermain",
                            "question" to "Jenis permainan apa yang cocok untuk anak autis usia 2-4 tahun?",
                            "options" to listOf(
                                "Permainan yang sangat kompleks",
                                "Permainan sensory dan repetitif sederhana",
                                "Hanya permainan digital",
                                "Permainan kompetitif"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Permainan sensory sederhana seperti bermain pasir, air, atau playdough membantu perkembangan sensori dan motorik. Permainan repetitif juga memberikan kenyamanan bagi anak autis."
                        ),
                        mapOf(
                            "aspect" to "Sensori",
                            "question" to "Apa yang harus dilakukan jika anak autis mengalami sensory overload?",
                            "options" to listOf(
                                "Memaksa anak untuk bertahan",
                                "Membawa anak ke tempat yang lebih tenang dan mengurangi stimulasi",
                                "Mengabaikan anak",
                                "Memberikan lebih banyak mainan"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Sensory overload adalah kondisi ketika stimulasi berlebihan. Solusinya adalah mengurangi stimulasi dengan membawa anak ke tempat tenang, meredupkan cahaya, atau mengurangi kebisingan."
                        ),
                        mapOf(
                            "aspect" to "Perilaku",
                            "question" to "Bagaimana cara menangani tantrum pada anak autis usia 2-4 tahun?",
                            "options" to listOf(
                                "Menghukum anak dengan keras",
                                "Tetap tenang, berikan ruang, dan cari tahu pemicunya",
                                "Mengabaikan sepenuhnya",
                                "Memberikan apapun yang anak mau"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Tantrum sering kali adalah cara anak mengekspresikan frustrasi. Tetap tenang, berikan ruang aman, dan setelah tenang, coba identifikasi pemicu untuk mencegah di masa depan."
                        )
                    )
                ),

                // Quiz untuk usia 5-7 tahun
                mapOf(
                    "ageSegment" to "5-7",
                    "title" to "Kuis Parenting Anak Autis (5-7 tahun)",
                    "duration" to 12,
                    "questions" to listOf(
                        mapOf(
                            "aspect" to "Pendidikan",
                            "question" to "Bagaimana mendukung anak autis usia 5-7 tahun di sekolah?",
                            "options" to listOf(
                                "Menyerahkan sepenuhnya kepada guru",
                                "Berkomunikasi aktif dengan guru dan membuat IEP (Individualized Education Program)",
                                "Tidak perlu melakukan apa-apa",
                                "Pindah sekolah terus-menerus"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Kolaborasi dengan guru dan membuat IEP yang disesuaikan dengan kebutuhan anak sangat penting. Ini memastikan anak mendapat dukungan yang tepat di lingkungan sekolah."
                        ),
                        mapOf(
                            "aspect" to "Sosial",
                            "question" to "Bagaimana membantu anak autis mengembangkan keterampilan sosial?",
                            "options" to listOf(
                                "Mengisolasi anak dari teman sebaya",
                                "Mengajarkan melalui role play dan social stories",
                                "Membiarkan anak mencari tahu sendiri",
                                "Memaksa anak untuk bersosialisasi"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Role play dan social stories adalah metode efektif untuk mengajarkan keterampilan sosial. Ini memberikan struktur dan contoh konkret yang mudah dipahami anak autis."
                        ),
                        mapOf(
                            "aspect" to "Kemandirian",
                            "question" to "Keterampilan apa yang penting diajarkan pada anak autis usia 5-7 tahun?",
                            "options" to listOf(
                                "Keterampilan akademis saja",
                                "Keterampilan hidup dasar seperti makan, berpakaian, dan toilet training",
                                "Tidak ada yang perlu diajarkan",
                                "Hanya keterampilan bermain"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Keterampilan hidup dasar sangat penting untuk kemandirian anak. Mengajarkan makan sendiri, berpakaian, dan toilet training membangun fondasi untuk kehidupan sehari-hari."
                        ),
                        mapOf(
                            "aspect" to "Komunikasi",
                            "question" to "Jika anak autis usia 5-7 tahun belum verbal, apa yang bisa dilakukan?",
                            "options" to listOf(
                                "Menyerah dan tidak mengajarkan komunikasi",
                                "Menggunakan AAC (Augmentative and Alternative Communication) seperti PECS atau tablet",
                                "Memaksa anak untuk berbicara",
                                "Mengabaikan kebutuhan komunikasi"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "AAC seperti Picture Exchange Communication System (PECS) atau aplikasi komunikasi di tablet dapat membantu anak non-verbal berkomunikasi. Ini juga dapat memfasilitasi perkembangan bahasa verbal."
                        ),
                        mapOf(
                            "aspect" to "Emosi",
                            "question" to "Bagaimana mengajarkan anak autis untuk mengenali emosi?",
                            "options" to listOf(
                                "Tidak perlu mengajarkan emosi",
                                "Menggunakan kartu emosi, cermin, dan memberi label pada perasaan",
                                "Menunggu sampai anak dewasa",
                                "Hanya memberi tahu saat anak salah"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Kartu emosi dengan wajah ekspresif, bermain di depan cermin, dan memberi label verbal pada perasaan membantu anak autis memahami dan mengekspresikan emosi mereka."
                        )
                    )
                ),

                // Quiz untuk usia 8-12 tahun
                mapOf(
                    "ageSegment" to "8-12",
                    "title" to "Kuis Parenting Anak Autis (8-12 tahun)",
                    "duration" to 15,
                    "questions" to listOf(
                        mapOf(
                            "aspect" to "Kemandirian",
                            "question" to "Bagaimana meningkatkan kemandirian anak autis usia 8-12 tahun?",
                            "options" to listOf(
                                "Melakukan semua hal untuk anak",
                                "Mengajarkan life skills secara bertahap dengan checklist dan visual supports",
                                "Membiarkan anak tanpa bimbingan",
                                "Menunggu sampai dewasa"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Mengajarkan keterampilan hidup secara sistematis dengan checklist visual membantu anak autis belajar kemandirian. Pecah tugas kompleks menjadi langkah-langkah kecil yang jelas."
                        ),
                        mapOf(
                            "aspect" to "Akademis",
                            "question" to "Strategi belajar apa yang efektif untuk anak autis di usia ini?",
                            "options" to listOf(
                                "Metode belajar yang sama dengan anak neurotipikal",
                                "Menggunakan interest-based learning dan visual aids",
                                "Tidak perlu strategi khusus",
                                "Fokus hanya pada hafalan"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Memanfaatkan minat khusus anak dan menggunakan bantuan visual membuat pembelajaran lebih menarik dan efektif. Ini meningkatkan motivasi dan pemahaman."
                        ),
                        mapOf(
                            "aspect" to "Sosial",
                            "question" to "Bagaimana membantu anak autis menghadapi bullying di sekolah?",
                            "options" to listOf(
                                "Menyuruh anak untuk melawan",
                                "Bekerja sama dengan sekolah, ajarkan assertiveness skills, dan bangun support system",
                                "Mengabaikan masalah",
                                "Menyalahkan anak"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Kolaborasi dengan sekolah, mengajarkan cara assertive (tegas tapi sopan), dan membangun support system sangat penting. Anak perlu tahu cara melaporkan dan mencari bantuan."
                        ),
                        mapOf(
                            "aspect" to "Teknologi",
                            "question" to "Bagaimana mengatur penggunaan gadget untuk anak autis usia 8-12 tahun?",
                            "options" to listOf(
                                "Memberikan akses unlimited",
                                "Menetapkan aturan waktu yang jelas, gunakan timer visual, dan pilih konten edukatif",
                                "Melarang total penggunaan gadget",
                                "Tidak perlu aturan"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Gadget bisa menjadi alat pembelajaran yang baik jika digunakan dengan bijak. Timer visual membantu anak memahami batasan waktu, dan konten edukatif mendukung perkembangan."
                        ),
                        mapOf(
                            "aspect" to "Perubahan",
                            "question" to "Anak autis sering kesulitan dengan perubahan. Bagaimana mempersiapkan mereka?",
                            "options" to listOf(
                                "Memberikan surprise agar terbiasa",
                                "Memberikan advance notice, visual schedule, dan social stories tentang perubahan",
                                "Tidak memberi tahu sama sekali",
                                "Memaksa anak untuk menerima"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Persiapan adalah kunci. Memberi tahu sebelumnya, menggunakan jadwal visual, dan membuat social stories tentang perubahan membantu anak autis beradaptasi dengan lebih baik."
                        ),
                        mapOf(
                            "aspect" to "Minat Khusus",
                            "question" to "Bagaimana memanfaatkan special interest anak autis secara produktif?",
                            "options" to listOf(
                                "Melarang minat khusus tersebut",
                                "Mengintegrasikan minat tersebut dalam pembelajaran dan aktivitas sosial",
                                "Membiarkan anak fokus hanya pada minat itu",
                                "Mengabaikan minat anak"
                            ),
                            "correctAnswerIndex" to 1,
                            "reason" to "Special interest bisa menjadi jembatan untuk pembelajaran dan sosialisasi. Gunakan minat tersebut sebagai motivator dan cara untuk mengajarkan keterampilan baru."
                        )
                    )
                )
            )

            val collection = firestore.collection("quiz_contents")
            quizContents.forEach { content ->
                collection.add(content).await()
            }

            Log.d("SEED", "✅ Quiz contents seeded: ${quizContents.size} quiz sets")
        } catch (e: Exception) {
            Log.e("SEED", "Error seeding quiz contents: ${e.message}", e)
        }
    }

    // Helper functions untuk tanggal
    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getTomorrowDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    private fun getDateAfterDays(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(calendar.time)
    }
}

