<div align="center">

# 🧩 Piyo
### *Smart Parenting Assistant for Children with Autism Spectrum Disorder*

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)
[![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)](https://firebase.google.com/)
[![AI](https://img.shields.io/badge/AI-Google%20Gemini-8E75B2?style=for-the-badge&logo=google&logoColor=white)](https://ai.google.dev/)

*Mendampingi Orang Tua, Mendukung Perkembangan Anak*

[📱 Fitur](#-fitur-utama) • [🏗️ Struktur](#️-struktur-project) • [🎯 Keunggulan](#-mengapa-piyo) • [🚀 Download](#-download--instalasi)

</div>

---

## 📖 Tentang Piyo

**Piyo** adalah aplikasi Android untuk membantu orang tua mengasuh anak dengan Autism Spectrum Disorder (ASD). Kami menggabungkan edukasi, penjadwalan terapi, kuis pemahaman, dan konsultasi AI dalam satu aplikasi.

### 🎯 Kenapa Piyo?

Mengasuh anak dengan autisme itu challenging. Orang tua sering kesulitan mencari informasi yang tepat, mengatur jadwal terapi yang padat, dan butuh tempat bertanya saat menghadapi situasi sulit. Aplikasi yang ada biasanya cuma fokus satu hal—entah cuma jadwal, atau cuma artikel.

Piyo hadir sebagai solusi lengkap dalam satu aplikasi.

---

## ✨ Fitur Utama

### 📚 **PiyoParent** - Edukasi & Kuis

Konten edukasi dari ahli psikologi tentang cara mengasuh anak dengan autisme. Ada artikel, video tutorial, dan tips praktis yang bisa langsung diterapkan.

Yang bikin beda: ada **kuis pemahaman** yang disesuaikan dengan usia anak Anda (2-4 tahun, 5-7 tahun, dst). Setelah kuis, Anda dapat skor, penjelasan jawaban, dan rekomendasi aktivitas yang cocok untuk anak.

### 📅 **PiyoPlan** - Jadwal & Reminder

Atur semua jadwal terapi dan aktivitas harian anak dalam satu kalender. Set reminder otomatis, tandai kegiatan yang sudah selesai, dan lihat progress anak dari hari ke hari.

Simple tapi powerful—tidak ada lagi terapi yang kelewatan.

### 🤖 **AskPiyo** - AI Chatbot

Butuh jawaban cepat tengah malam? Chat dengan AI assistant yang paham tentang autisme. Tanya apa saja—dari cara handle tantrum, tips berkomunikasi, sampai ide aktivitas.

Bonus: bisa pakai voice input dan chatbot bisa jawab pakai suara juga.

### 📊 **Dashboard & Insight**

Lihat ringkasan aktivitas anak, grafik perkembangan, dan statistik kegiatan. Ada mood tracker juga buat catat suasana hati anak setiap hari.

### ⚙️ **Pengaturan & Profil**

Kelola data anak, upload foto, atur notifikasi, dan akses pusat bantuan.

---

## 🏗️ Struktur Project

Piyo dibangun dengan arsitektur **MVVM (Model-View-ViewModel)** dan mengikuti prinsip **Clean Architecture** untuk memisahkan business logic dari UI.

```
Piyo/
│
├── app/src/main/java/com/example/piyo/
│   │
│   ├── 📂 data/                          # Data Layer
│   │   ├── local/                        # Room Database & SharedPreferences
│   │   ├── remote/                       # Firebase Services
│   │   ├── repository/                   # Repository Implementations
│   │   └── dto/                          # Data Transfer Objects
│   │
│   ├── 📂 domain/                        # Domain Layer (Business Logic)
│   │   ├── model/                        # Domain Models
│   │   ├── repository/                   # Repository Interfaces
│   │   └── usecase/                      # Use Cases
│   │
│   ├── 📂 presentation/                  # UI Layer
│   │   ├── auth/                         # Login & Register
│   │   ├── home/                         # Main Features
│   │   │   ├── beranda/                  # Dashboard
│   │   │   ├── parent/                   # PiyoParent & Chatbot
│   │   │   ├── insight/                  # Progress Charts
│   │   │   └── settings/                 # Settings
│   │   ├── quiz/                         # Quiz Feature
│   │   ├── piyoplan/                     # Schedule Feature
│   │   └── infoanak/                     # Child Info
│   │
│   ├── 📂 di/                            # Dependency Injection (Koin)
│   ├── 📂 utils/                         # Helper Classes
│   └── MainActivity.kt                   # Entry Point
│
├── 📄 README.md
└── 📄 build.gradle.kts
```

### Tech Stack:
- **Kotlin** - Modern, concise, safe
- **Jetpack Compose** - Declarative UI
- **Firebase** - Authentication, Firestore, Storage
- **Room Database** - Offline-first local storage
- **Koin** - Dependency injection
- **Gemini AI** - Chatbot intelligence
- **Coroutines & Flow** - Asynchronous operations

---

## 🎯 Mengapa Piyo?

| Aplikasi Lain | Piyo |
|---------------|------|
| Fokus cuma satu fitur | All-in-one platform |
| Tidak ada AI chatbot | Konsultasi 24/7 dengan AI |
| Info umum parenting | Spesifik untuk autisme |
| Tidak ada tracking | Progress tracking lengkap |

---

## 🚀 Setup Project dari GitHub

### Prerequisites:
- Android Studio Hedgehog (2023.1.1) atau lebih baru
- JDK 11+
- Android SDK 29+ (Target SDK 36)
- Akun Firebase

### Langkah Instalasi:

1. **Clone Repository**
   ```bash
   git clone https://github.com/yourusername/piyo.git
   cd piyo
   ```

2. **Setup Firebase**
   - Buat project di [Firebase Console](https://console.firebase.google.com/)
   - Download `google-services.json`
   - Letakkan di folder `app/`
   - Enable Authentication, Firestore, dan Storage

3. **Setup Gemini API**
   - Dapatkan API key dari [Google AI Studio](https://aistudio.google.com/app/apikey)
   - Buat file `local.properties` di root project:
   ```properties
   sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
   GEMINI_API_KEY=your_api_key_here
   ```

4. **Sync & Build**
   - Buka project di Android Studio
   - Sync Gradle
   - Build & Run

---

## 👥 Tim Pengembang

<div align="center">

| Role | Nama |
|:----:|:----:|
| **Project Manager** | Rizkyka Atila Zakiya |
| **UI/UX Designer** | Fira Zaha Iklila |
| **Programmer** | Ghufron Bagaskara |
| **Programmer** | Ferrel Destatiananda Edwardo |
| **Programmer** | Ananda Fifadlika |

</div>

---

## 🙏 Credits

Terima kasih kepada:
- Komunitas Autisme Indonesia
- Psikolog & Terapis yang memvalidasi konten
- Orang tua yang sudah memberikan feedback
- Google (Gemini AI) & Firebase

---

## 📄 Lisensi

MIT License - bebas digunakan dan dimodifikasi dengan mencantumkan credit.

---

<div align="center">

### ⭐ Star repo ini jika Piyo membantu!

**"Setiap anak istimewa, setiap orang tua luar biasa"**

© 2026 Piyo. All Rights Reserved.

</div>
