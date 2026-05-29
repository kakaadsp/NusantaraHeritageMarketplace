package com.upn.nusantaraheritagemarketplace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data Class HeritageItem
 * Mewarisi Abstract Class Heritage dan mengimplementasikan Parcelable
 * agar objek dapat dikirimkan antar Activity melalui Intent.
 *
 * @param id          ID unik warisan budaya
 * @param name        Nama warisan budaya
 * @param origin      Daerah asal warisan budaya
 * @param category    Kategori (mis: Cagar Budaya, Seni Pertunjukan, dll)
 * @param year        Tahun ditemukan/dicatat
 * @param description Deskripsi lengkap warisan budaya
 * @param imageResId  Resource ID gambar drawable lokal
 * @param imageUrl    URL gambar untuk diload via Glide (opsional)
 * @param location    Lokasi untuk Google Maps
 */
@Parcelize
data class HeritageItem(
    val id: Int,
    val name: String,
    val origin: String,
    val category: String,
    val year: Int,
    val description: String,
    val imageResId: Int,
    val imageUrl: String = "",
    val location: String
) : Heritage(), Parcelable {

    /**
     * Implementasi metode abstrak dari Heritage.
     * Mengembalikan informasi detail kategori warisan budaya.
     */
    override fun getCategoryDetails(): String {
        return "Kategori: $category | Asal: $origin | Tahun: $year M"
    }

    /**
     * Menentukan tingkat kelangkaan berdasarkan tahun penemuan.
     * Menggunakan Control Flow (when) sesuai spesifikasi:
     * - < 1900   : Sangat Langka
     * - 1900-2000: Langka
     * - > 2000   : Terlindungi
     */
    fun getRarityLevel(): String {
        return when {
            year < 1900 -> "Sangat Langka"
            year in 1900..2000 -> "Langka"
            else -> "Terlindungi"
        }
    }

    /**
     * Mengembalikan URL artikel Wikipedia berdasarkan nama warisan budaya.
     */
    fun getWikipediaUrl(): String {
        val searchQuery = name.replace(" ", "_")
        return "https://id.wikipedia.org/wiki/$searchQuery"
    }
}
