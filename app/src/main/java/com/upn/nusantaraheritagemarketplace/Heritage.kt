package com.upn.nusantaraheritagemarketplace

/**
 * Abstract Class Heritage
 * Mendefinisikan kontrak untuk semua objek warisan budaya.
 * Setiap subclass WAJIB mengimplementasikan getCategoryDetails().
 */
abstract class Heritage {
    /**
     * Metode abstrak yang harus diimplementasikan oleh subclass.
     * Mengembalikan detail kategori warisan budaya dalam format string.
     */
    abstract fun getCategoryDetails(): String
}
