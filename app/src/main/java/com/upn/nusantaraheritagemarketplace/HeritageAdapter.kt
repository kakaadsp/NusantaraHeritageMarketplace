package com.upn.nusantaraheritagemarketplace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

/**
 * HeritageAdapter
 * Adapter untuk RecyclerView yang menampilkan daftar warisan budaya.
 * Mendukung dua jenis tampilan: LIST (Linear) dan GRID (2 Kolom).
 * Menggunakan OnHeritageClickListener interface untuk menangani aksi klik secara profesional.
 */
class HeritageAdapter(
    private val heritageList: List<HeritageItem>,
    private val listener: OnHeritageClickListener
) : RecyclerView.Adapter<HeritageAdapter.HeritageViewHolder>() {

    companion object {
        const val VIEW_TYPE_LIST = 0
        const val VIEW_TYPE_GRID = 1
    }

    // Tipe tampilan saat ini: LIST atau GRID
    var viewType: Int = VIEW_TYPE_LIST
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * ViewHolder yang menyimpan referensi ke view-view dalam item layout.
     */
    inner class HeritageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgHeritage: ShapeableImageView = view.findViewById(R.id.imgHeritage)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvOrigin: TextView = view.findViewById(R.id.tvOrigin)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvRarity: TextView = view.findViewById(R.id.tvRarity)
        val tvYear: TextView = view.findViewById(R.id.tvYear)

        init {
            // Set click listener menggunakan OnHeritageClickListener interface
            view.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onHeritageClick(heritageList[position], position)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeritageViewHolder {
        val layout = if (viewType == VIEW_TYPE_GRID) {
            R.layout.item_heritage_grid
        } else {
            R.layout.item_heritage_list
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return HeritageViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeritageViewHolder, position: Int) {
        val item = heritageList[position]

        // Bind text data
        holder.tvName.text = item.name
        holder.tvOrigin.text = "📍 ${item.origin}"
        holder.tvCategory.text = item.category
        holder.tvYear.text = "📅 ${item.year} M"

        // Tentukan tingkat kelangkaan menggunakan when (Control Flow - sesuai spesifikasi)
        val rarity = item.getRarityLevel()
        holder.tvRarity.text = rarity

        // Set warna chip kelangkaan menggunakan when (Control Flow)
        val rarityBgRes = when (rarity) {
            "Sangat Langka" -> R.drawable.bg_chip_very_rare
            "Langka" -> R.drawable.bg_chip_rare
            else -> R.drawable.bg_chip_protected
        }
        holder.tvRarity.setBackgroundResource(rarityBgRes)

        // Load gambar menggunakan Glide (URL atau drawable resource)
        val imageSource: Any = if (item.imageUrl.isNotEmpty()) {
            item.imageUrl
        } else {
            item.imageResId
        }

        Glide.with(holder.itemView.context)
            .load(imageSource)
            .placeholder(R.drawable.img_heritage_placeholder)
            .error(R.drawable.img_heritage_placeholder)
            .centerCrop()
            .into(holder.imgHeritage)
    }

    override fun getItemCount(): Int = heritageList.size
}
