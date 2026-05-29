<div align="center">

# 🏛️ Nusantara Heritage Marketplace

<img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Min_SDK-26-8B1A1A?style=for-the-badge&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Target_SDK-35-D4A843?style=for-the-badge&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Build-Passing-2E7D32?style=for-the-badge&logo=gradle&logoColor=white"/>

<br/>

> **Aplikasi katalog interaktif warisan budaya Nusantara** — Jelajahi, temukan, dan tandai warisan budaya Indonesia yang kaya dan beragam.

<br/>

```
🔴 Sangat Langka   🔵 Langka   🟢 Terlindungi
```

</div>

---

## 📱 Tampilan Aplikasi

<div align="center">

| 📋 Mode List | ⊞ Mode Grid | 🔍 Detail |
|:---:|:---:|:---:|
| Tampilan daftar vertikal dengan informasi lengkap | 2-kolom grid compact view | Halaman detail dengan collapsing toolbar |

</div>

---

## ✨ Fitur Utama

<table>
<tr>
<td width="50%">

### 🎨 UI Dinamis
- **RecyclerView** dengan 2 mode tampilan:
  - 📋 `LinearLayoutManager` — List Vertikal
  - ⊞ `GridLayoutManager` — Grid 2 Kolom
- **ShapeableImageView** sudut melengkung
- **CollapsingToolbarLayout** di halaman detail
- Tema batik nusantara 🔴 + 🟡

</td>
<td width="50%">

### 🔗 Navigasi Dua Arah
- **Explicit Intent** MainActivity → DetailActivity
- **`registerForActivityResult`** untuk data balik
- Status favorit ditampilkan real-time di halaman utama
- Back navigation dengan `finish()` + `setResult()`

</td>
</tr>
<tr>
<td width="50%">

### 🌍 Implicit Intent
- **📍 Google Maps** — Lihat lokasi situs warisan langsung di Maps
- **🌐 Wikipedia** — Baca artikel lengkap di browser
- Fallback otomatis jika Google Maps tidak tersedia

</td>
<td width="50%">

### ⭐ Sistem Kelangkaan
Menggunakan **Control Flow `when`**:

| Tahun | Tingkat |
|-------|---------|
| `< 1900` | 🟣 **Sangat Langka** |
| `1900–2000` | 🔵 **Langka** |
| `> 2000` | 🟢 **Terlindungi** |

</td>
</tr>
</table>

---

## 🏗️ Arsitektur OOP

```
📦 com.upn.nusantaraheritagemarketplace
│
├── 🔷 Heritage.kt                    ← Abstract Class
│       └── getCategoryDetails()       ← Metode Abstrak
│
├── 🔶 OnHeritageClickListener.kt     ← Interface
│       └── onHeritageClick()          ← Click Handler
│
├── 💎 HeritageItem.kt                ← Data Class
│       ├── implements Heritage         ← Extends Abstract
│       ├── implements Parcelable       ← Antar-Activity transfer
│       └── getRarityLevel()           ← when() Control Flow
│
├── 🔄 HeritageAdapter.kt             ← RecyclerView Adapter
│       ├── VIEW_TYPE_LIST             ← Linear Layout
│       └── VIEW_TYPE_GRID             ← Grid Layout
│
├── 📱 MainActivity.kt                ← Halaman Utama
│       ├── registerForActivityResult  ← Tangkap data balik
│       ├── LinearLayoutManager        ← Default view
│       └── GridLayoutManager          ← Toggle view
│
└── 🔍 DetailActivity.kt              ← Halaman Detail
        ├── Parcelable (Intent data)   ← Terima objek
        ├── setResult(RESULT_OK)       ← Kirim balik favorit
        ├── openGoogleMaps()           ← Implicit Intent
        └── openBrowser()              ← Implicit Intent
```

---

## 🗂️ Dataset Warisan Budaya

| No | Nama | Asal | Kategori | Tahun | Kelangkaan |
|----|------|------|----------|-------|------------|
| 1 | 🏛️ Candi Borobudur | Magelang, Jawa Tengah | Cagar Budaya | 825 | 🟣 Sangat Langka |
| 2 | 🎨 Batik Jawa | Jawa, Indonesia | Seni Tekstil | 1800 | 🟣 Sangat Langka |
| 3 | 🎭 Wayang Kulit | Jawa, Indonesia | Seni Pertunjukan | 900 | 🟣 Sangat Langka |
| 4 | 💃 Tari Kecak Bali | Bali, Indonesia | Tari Tradisional | 1930 | 🔵 Langka |
| 5 | ⚔️ Keris Nusantara | Nusantara, Indonesia | Senjata Tradisional | 600 | 🟣 Sangat Langka |
| 6 | 🏠 Rumah Gadang | Sumatera Barat | Arsitektur Tradisional | 1800 | 🟣 Sangat Langka |
| 7 | 🎵 Gamelan Jawa | Jawa, Indonesia | Musik Tradisional | 900 | 🟣 Sangat Langka |
| 8 | 🧶 Tenun Ikat NTT | Nusa Tenggara Timur | Seni Tekstil | 500 | 🟣 Sangat Langka |
| 9 | 🕺 Tari Saman Aceh | Aceh, Indonesia | Tari Tradisional | 1200 | 🟣 Sangat Langka |
| 10 | 🏘️ Tongkonan Toraja | Sulawesi Selatan | Arsitektur Tradisional | 1000 | 🟣 Sangat Langka |

---

## 🛠️ Tech Stack

<div align="center">

| Teknologi | Versi | Kegunaan |
|-----------|-------|---------|
| ![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-7F52FF?logo=kotlin&logoColor=white) | 2.1.0 | Bahasa pemrograman utama |
| ![Android](https://img.shields.io/badge/AGP-8.7.3-3DDC84?logo=android&logoColor=white) | 8.7.3 | Android Gradle Plugin |
| ![Material](https://img.shields.io/badge/Material_3-1.12.0-6200EE?logo=materialdesign&logoColor=white) | 1.12.0 | UI Components |
| ![RecyclerView](https://img.shields.io/badge/RecyclerView-1.4.0-0288D1?logo=android&logoColor=white) | 1.4.0 | Daftar dinamis |
| ![Glide](https://img.shields.io/badge/Glide-4.16.0-FF6F00?logo=android&logoColor=white) | 4.16.0 | Image loading & caching |
| ![Parcelize](https://img.shields.io/badge/Parcelize-Plugin-7F52FF?logo=kotlin&logoColor=white) | built-in | Serialisasi objek antar Activity |

</div>

---

## 🚀 Cara Menjalankan

### Prasyarat
- **Android Studio** Hedgehog atau lebih baru
- **JDK 11** (bundled dengan Android Studio)
- **Android SDK** API 26+

### Langkah Instalasi

```bash
# 1. Clone repository
git clone https://github.com/kakaadsp/NusantaraHeritageMarketplace.git

# 2. Buka di Android Studio
# File → Open → pilih folder NusantaraHeritageMarketplace

# 3. Sync Gradle (otomatis)
# Tunggu hingga sync selesai

# 4. Jalankan di emulator/device
# Klik tombol ▶ Run atau Shift+F10
```

### Build APK Debug
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

---

## 📋 Spesifikasi Teknis

<details>
<summary><b>📌 Klik untuk melihat detail spesifikasi</b></summary>

### I. Arsitektur OOP Tingkat Lanjut
- ✅ **Abstract Class** `Heritage` dengan metode abstrak `getCategoryDetails()`
- ✅ **Interface** `OnHeritageClickListener` untuk aksi klik profesional via adapter
- ✅ **Data Class** `HeritageItem` yang mewarisi `Heritage` + mengimplementasikan `Parcelable`

### II. Dataset & Kontrol Alur
- ✅ **10 data** warisan budaya via `string-array` dan `integer-array` di `strings.xml`
- ✅ **Control Flow `when`** untuk Tingkat Kelangkaan:
  - `< 1900` → **Sangat Langka**
  - `1900–2000` → **Langka**  
  - `> 2000` → **Terlindungi**

### III. UI Dinamis
- ✅ **ConstraintLayout** kompleks di dalam **CardView**
- ✅ Toggle `LinearLayoutManager` ↔ `GridLayoutManager` (2 kolom) via menu toolbar

### IV. Navigasi Dua Arah
- ✅ **Explicit Intent** membuka DetailActivity
- ✅ **`registerForActivityResult`** menangkap status favorit dari DetailActivity

### V. Output & Interaksi
- ✅ **ShapeableImageView** dengan sudut melengkung
- ✅ **Toast** kategori saat item dipilih
- ✅ **Status Favorit** (Ya/Tidak) diperbarui otomatis di halaman utama
- ✅ **Implicit Intent** Google Maps (lokasi situs)
- ✅ **Implicit Intent** Browser (artikel Wikipedia)

</details>

---

## 📁 Struktur Project

```
NusantaraHeritageMarketplace/
├── app/
│   └── src/main/
│       ├── java/.../nusantaraheritagemarketplace/
│       │   ├── Heritage.kt                  ← Abstract Class
│       │   ├── HeritageItem.kt              ← Data Class + Parcelable
│       │   ├── OnHeritageClickListener.kt   ← Interface
│       │   ├── HeritageAdapter.kt           ← RecyclerView Adapter
│       │   ├── MainActivity.kt              ← Halaman Utama
│       │   └── DetailActivity.kt            ← Halaman Detail
│       ├── res/
│       │   ├── layout/
│       │   │   ├── activity_main.xml        ← Layout utama
│       │   │   ├── activity_detail.xml      ← Layout detail
│       │   │   ├── item_heritage_list.xml   ← Item list view
│       │   │   └── item_heritage_grid.xml   ← Item grid view
│       │   ├── drawable/                    ← Icons & backgrounds
│       │   ├── menu/menu_main.xml           ← Menu toggle layout
│       │   └── values/
│       │       ├── strings.xml              ← Data heritage (10 items)
│       │       ├── colors.xml               ← Palette batik nusantara
│       │       └── themes.xml               ← Material Components theme
│       └── AndroidManifest.xml
├── build.gradle.kts
├── gradle/libs.versions.toml
└── README.md
```

---

## 🎨 Desain & Tema

Terinspirasi dari **estetika batik nusantara** dengan palet warna:

```
🔴 Merah Marun    #8B1A1A   → Primary (Toolbar, Chip)
🟡 Emas           #D4A843   → Secondary (Aksen, Border)  
🟢 Hijau Teal     #2C7873   → Accent (Tombol Maps)
⚪ Krem           #FBF5EB   → Background
```

---

## 👨‍💻 Developer

<div align="center">

**Dikerjakan untuk memenuhi tugas mata kuliah Pemrograman Mobile**

[![GitHub](https://img.shields.io/badge/GitHub-kakaadsp-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/kakaadsp)

*Deadline: Jumat, 29 Mei 2026 — 19.00 WIB*

---

<sub>Made with ❤️ and ☕ — Nusantara Heritage Marketplace © 2026</sub>

</div>
