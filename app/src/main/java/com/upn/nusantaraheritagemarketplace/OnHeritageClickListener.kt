package com.upn.nusantaraheritagemarketplace

/**
 * Interface OnHeritageClickListener
 * Menangani aksi klik item pada RecyclerView secara profesional melalui adapter.
 * Pattern ini memisahkan logika klik dari adapter sehingga lebih mudah diuji.
 */
interface OnHeritageClickListener {
    /**
     * Dipanggil ketika pengguna menekan item warisan budaya.
     * @param item Data warisan budaya yang diklik
     * @param position Posisi item dalam RecyclerView
     */
    fun onHeritageClick(item: HeritageItem, position: Int)
}
