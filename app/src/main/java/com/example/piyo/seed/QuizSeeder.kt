package com.example.piyo.seed

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Seed Quiz Data to Firestore
 * Call this once to populate quiz data based on age segments
 */
class QuizSeeder(private val firestore: FirebaseFirestore) {

    suspend fun seedAllQuizzes() {
        seedBalitaQuiz()
        seedSDQuiz()
        seedPraremajaQuiz()
        seedRemajaQuiz()
    }

    private suspend fun seedBalitaQuiz() {
        val quiz = mapOf(
            "ageSegment" to "balita",
            "title" to "Kuis Parenting Balita (2-5 tahun)",
            "duration" to 900,
            "questions" to listOf(
                mapOf(
                    "aspect" to "Komunikasi & Bahasa",
                    "question" to "Bagaimana orang tua bisa membantu anak mulai berkomunikasi walau belum bicara?",
                    "options" to listOf(
                        "Menunggu sampai anak bisa bicara sendiri",
                        "Menggunakan gesture, gambar, atau kartu visual",
                        "Sering menegur anak agar bicara"
                    ),
                    "correctAnswerIndex" to 1,
                    "reason" to "Menggunakan alat bantu visual membantu anak berkomunikasi"
                ),
                mapOf(
                    "aspect" to "Sosial & Interaksi",
                    "question" to "Apa langkah pertama untuk mengenalkan anak ke teman sebaya?",
                    "options" to listOf(
                        "Memaksa anak bergabung dalam permainan besar",
                        "Mengatur pertemuan singkat dan terstruktur dengan satu teman",
                        "Membiarkan anak bermain sendiri terus"
                    ),
                    "correctAnswerIndex" to 1,
                    "reason" to "Pertemuan kecil dan terstruktur lebih efektif"
                ),
                mapOf(
                    "aspect" to "Emosi & Perilaku",
                    "question" to "Bagaimana menghadapi tantrum anak tanpa membuatnya makin stres?",
                    "options" to listOf(
                        "Berteriak untuk menghentikan tantrum",
                        "Mengabaikan seluruh perilaku anak",
                        "Tenangkan diri dulu, lalu bantu anak mengenali perasaannya"
                    ),
                    "correctAnswerIndex" to 2,
                    "reason" to "Menenangkan diri terlebih dahulu membantu situasi"
                ),
                mapOf(
                    "aspect" to "Sensorik & Motorik",
                    "question" to "Apa yang sebaiknya dilakukan jika anak terganggu dengan suara keras?",
                    "options" to listOf(
                        "Mematikan suara atau menjauh dari sumber suara",
                        "Memaksa anak terbiasa",
                        "Menertawakan reaksi anak agar terbiasa"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Menghindari stimulus yang mengganggu adalah solusi terbaik"
                ),
                mapOf(
                    "aspect" to "Kemandirian",
                    "question" to "Bagaimana melatih anak agar mau makan sendiri?",
                    "options" to listOf(
                        "Biarkan mencoba walau berantakan",
                        "Suapi terus agar cepat selesai",
                        "Tunggu sampai anak besar"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Membiarkan anak mencoba membangun kemandirian"
                )
            )
        )

        firestore.collection("quizzes").add(quiz).await()
    }

    private suspend fun seedSDQuiz() {
        val quiz = mapOf(
            "ageSegment" to "sd",
            "title" to "Kuis Parenting Anak SD (6-9 tahun)",
            "duration" to 900,
            "questions" to listOf(
                mapOf(
                    "aspect" to "Komunikasi & Bahasa",
                    "question" to "Bagaimana membantu anak memahami emosi orang lain?",
                    "options" to listOf(
                        "Melalui cerita dan gambar ekspresi wajah",
                        "Dengan memberi hukuman jika anak salah tanggap",
                        "Menghindari pembicaraan tentang emosi"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Cerita dan visual membantu pemahaman emosi"
                ),
                mapOf(
                    "aspect" to "Sosial & Interaksi",
                    "question" to "Apa cara terbaik melatih anak bergabung dalam permainan kelompok?",
                    "options" to listOf(
                        "Memberi contoh dulu lalu ajak bergabung perlahan",
                        "Memaksa anak ikut bermain",
                        "Membiarkan anak diam di pojok"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Modeling dan pendekatan bertahap efektif"
                ),
                mapOf(
                    "aspect" to "Emosi & Perilaku",
                    "question" to "Bagaimana menenangkan anak yang cemas di lingkungan sekolah?",
                    "options" to listOf(
                        "Bicara dengan lembut dan beri benda yang menenangkan",
                        "Membiarkan anak menangis sendiri",
                        "Menegur di depan teman-temannya"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Pendekatan lembut dan objek transisi membantu"
                ),
                mapOf(
                    "aspect" to "Sensorik & Motorik",
                    "question" to "Bagaimana mengatur ruang belajar agar nyaman bagi anak sensitif sensorik?",
                    "options" to listOf(
                        "Minimalkan cahaya dan suara bising",
                        "Nyalakan musik keras",
                        "Pasang banyak dekorasi mencolok"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Lingkungan tenang mendukung fokus"
                ),
                mapOf(
                    "aspect" to "Kemandirian",
                    "question" to "Bagaimana mengajarkan rutinitas harian seperti menyikat gigi?",
                    "options" to listOf(
                        "Gunakan jadwal visual dan pengingat positif",
                        "Tegur keras jika lupa",
                        "Biarkan saja sampai terbiasa sendiri"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Visual schedule membantu konsistensi"
                )
            )
        )

        firestore.collection("quizzes").add(quiz).await()
    }

    private suspend fun seedPraremajaQuiz() {
        val quiz = mapOf(
            "ageSegment" to "praremaja",
            "title" to "Kuis Parenting Praremaja (10-12 tahun)",
            "duration" to 900,
            "questions" to listOf(
                mapOf(
                    "aspect" to "Komunikasi & Bahasa",
                    "question" to "Bagaimana membantu anak berani menyampaikan pendapat di rumah?",
                    "options" to listOf(
                        "Dengarkan tanpa mengoreksi terus-menerus",
                        "Abaikan agar anak belajar mandiri",
                        "Suruh menulis saja tanpa bicara"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Mendengarkan aktif membangun kepercayaan diri"
                ),
                mapOf(
                    "aspect" to "Sosial & Interaksi",
                    "question" to "Bagaimana membantu anak memahami aturan sosial di lingkungan baru?",
                    "options" to listOf(
                        "Latih melalui role-play atau simulasi situasi sosial",
                        "Biarkan belajar sendiri",
                        "Jangan biarkan keluar rumah"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Role-play efektif untuk latihan sosial"
                ),
                mapOf(
                    "aspect" to "Emosi & Perilaku",
                    "question" to "Bagaimana menghadapi anak yang mulai menarik diri karena merasa berbeda?",
                    "options" to listOf(
                        "Beri ruang aman dan ajak bicara terbuka",
                        "Suruh bergaul paksa",
                        "Diamkan agar terbiasa"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Ruang aman dan komunikasi terbuka penting"
                ),
                mapOf(
                    "aspect" to "Sensorik & Motorik",
                    "question" to "Bagaimana menyesuaikan aktivitas agar anak tetap fokus tanpa stres?",
                    "options" to listOf(
                        "Bagi aktivitas jadi sesi singkat dengan jeda",
                        "Lakukan terus tanpa istirahat",
                        "Jangan beri tugas apa pun"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Break time membantu menjaga fokus"
                ),
                mapOf(
                    "aspect" to "Kemandirian",
                    "question" to "Bagaimana melatih anak membuat keputusan kecil sendiri di rumah?",
                    "options" to listOf(
                        "Beri dua pilihan sederhana untuk dipilih sendiri",
                        "Pilihkan semua hal untuknya",
                        "Hindari keputusan agar tidak bingung"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Pilihan terbatas membangun kemampuan memilih"
                )
            )
        )

        firestore.collection("quizzes").add(quiz).await()
    }

    private suspend fun seedRemajaQuiz() {
        val quiz = mapOf(
            "ageSegment" to "remaja",
            "title" to "Kuis Parenting Remaja (13-17 tahun)",
            "duration" to 900,
            "questions" to listOf(
                mapOf(
                    "aspect" to "Komunikasi & Bahasa",
                    "question" to "Bagaimana cara membantu remaja autisme berkomunikasi secara efektif dengan orang lain?",
                    "options" to listOf(
                        "Latih dengan simulasi percakapan nyata",
                        "Hindari percakapan sulit",
                        "Biarkan mereka belajar sendiri"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Simulasi membantu praktik komunikasi real-world"
                ),
                mapOf(
                    "aspect" to "Sosial & Interaksi",
                    "question" to "Bagaimana mendukung remaja autisme membangun pertemanan sehat?",
                    "options" to listOf(
                        "Dorong minat yang sama dengan teman",
                        "Paksakan ikut semua kegiatan",
                        "Biarkan tanpa arahan"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Minat bersama mempermudah koneksi sosial"
                ),
                mapOf(
                    "aspect" to "Emosi & Perilaku",
                    "question" to "Bagaimana membantu remaja mengelola stres akademik?",
                    "options" to listOf(
                        "Ajak diskusi solusi dan bantu buat jadwal realistis",
                        "Tekan agar belajar lebih keras",
                        "Biarkan tanpa pendampingan"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Kolaborasi dan perencanaan realistis mengurangi stres"
                ),
                mapOf(
                    "aspect" to "Sensorik & Motorik",
                    "question" to "Apa cara terbaik mendukung remaja dengan sensitivitas sensorik di ruang publik?",
                    "options" to listOf(
                        "Biarkan membawa alat bantu (earphone, kacamata gelap)",
                        "Larang agar terlihat \"normal\"",
                        "Biarkan tanpa dukungan"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Alat bantu sensorik meningkatkan kenyamanan"
                ),
                mapOf(
                    "aspect" to "Kemandirian",
                    "question" to "Bagaimana membantu remaja menyiapkan masa depan (kuliah/kerja)?",
                    "options" to listOf(
                        "Kenalkan kegiatan sesuai minat dan kemampuan",
                        "Biarkan tanpa arah",
                        "Tekan untuk ikut pilihan orang tua"
                    ),
                    "correctAnswerIndex" to 0,
                    "reason" to "Eksplorasi minat membantu transisi ke dewasa"
                )
            )
        )

        firestore.collection("quizzes").add(quiz).await()
    }
}

