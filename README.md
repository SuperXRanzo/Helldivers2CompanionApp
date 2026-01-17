# 🚀 Helldivers 2 Companion App

Aplikasi mobile Android untuk membantu para Helldivers mengelola strategi, loadout, dan memantau status perang galaksi. Dibangun sebagai Tugas Akhir Mata Kuliah Mobile Programming.

## 👨‍💻 Identitas Mahasiswa
* **Nama:** Yola Octaviano Lukito
* **NIM:** 20230140038
* **Kelas:** A

## 📱 Fitur Utama

### 1. User System (Helldiver)
* **Authentication:** Login & Register aman menggunakan Room Database.
* **Dashboard:** Tampilan statistik tempur (Kills, Deaths, K/D Ratio) dan navigasi menu.
* **Custom Loadout Manager:** Fitur unggulan untuk meracik senjata (Primary/Secondary) dan 4 Stratagem, lalu menyimpannya ke database lokal.
* **Profile System:** Halaman profil dengan visualisasi statistik dan level karakter.

### 2. Admin System (Super Earth Command)
* **Login Khusus Admin:** Akses dashboard manajemen.
* **CRUD Data:** Admin dapat Menambah, Mengedit, dan Menghapus data:
  * Daftar Misi (Missions)
  * Daftar Stratagem (Persenjataan)
  * Daftar Senjata (Weapons)

## 🛠️ Teknologi yang Digunakan
Aplikasi ini dibangun menggunakan **Native Android (Kotlin)** dengan arsitektur modern:
* **Database:** Room Database (SQLite) untuk penyimpanan data lokal (Offline-first).
* **UI Design:** XML Layouting dengan implementasi **ViewBinding**.
* **Concurrency:** Kotlin Coroutines untuk operasi database asynchronous (anti-lag).
* **Architecture:** Menggunakan pola MVVM (Model-View-ViewModel) sederhana.

## 📸 Cara Menjalankan Aplikasi
1. Clone repository ini.
2. Buka di **Android Studio Ladybug / Jellyfish** (atau versi terbaru).
3. Biarkan Gradle melakukan sinkronisasi (*Sync Project*).
4. Klik **Run** pada Emulator atau Device fisik.
5. **PENTING:** Saat pertama kali aplikasi dibuka, klik tombol **"[DEV: Generate Dummy Data]"** di halaman Login untuk mengisi database dengan data awal (User, Misi, Stratagem).

---
*"For Super Earth! Democracy never sleeps."* 🫡
