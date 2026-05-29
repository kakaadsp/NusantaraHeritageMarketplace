package com.upn.nusantaraheritagemarketplace

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

/**
 * DetailActivity - Halaman Detail Warisan Budaya
 *
 * Fitur:
 * - Menampilkan informasi lengkap objek HeritageItem yang dikirim via Explicit Intent
 * - Tombol "Tandai Favorit" untuk mengirim data balik ke MainActivity
 * - Tombol Implicit Intent untuk Google Maps dan Browser
 * - Memanggil finish() setelah mengirim data balik
 */
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HERITAGE_ITEM = "extra_heritage_item"
        const val EXTRA_IS_FAVORITE = "extra_is_favorite"
        const val EXTRA_ITEM_NAME = "extra_item_name"
    }

    private lateinit var heritageItem: HeritageItem
    private var isFavorite = false

    // Views
    private lateinit var imgDetailHero: ShapeableImageView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var toolbarDetail: Toolbar
    private lateinit var tvDetailName: TextView
    private lateinit var tvDetailCategory: TextView
    private lateinit var tvDetailOrigin: TextView
    private lateinit var tvDetailYear: TextView
    private lateinit var tvDetailRarity: TextView
    private lateinit var tvDetailDescription: TextView
    private lateinit var tvCurrentFavoriteStatus: TextView
    private lateinit var btnFavorite: MaterialButton
    private lateinit var btnOpenMaps: MaterialButton
    private lateinit var btnOpenBrowser: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Ambil data HeritageItem dari Intent (Parcelable)
        heritageItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_HERITAGE_ITEM, HeritageItem::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_HERITAGE_ITEM)!!
        }

        // Init Views
        initViews()

        // Setup Toolbar
        setupToolbar()

        // Tampilkan data
        bindData()

        // Setup click listeners
        setupClickListeners()
    }

    private fun initViews() {
        imgDetailHero = findViewById(R.id.imgDetailHero)
        collapsingToolbar = findViewById(R.id.collapsingToolbar)
        toolbarDetail = findViewById(R.id.toolbarDetail)
        tvDetailName = findViewById(R.id.tvDetailName)
        tvDetailCategory = findViewById(R.id.tvDetailCategory)
        tvDetailOrigin = findViewById(R.id.tvDetailOrigin)
        tvDetailYear = findViewById(R.id.tvDetailYear)
        tvDetailRarity = findViewById(R.id.tvDetailRarity)
        tvDetailDescription = findViewById(R.id.tvDetailDescription)
        tvCurrentFavoriteStatus = findViewById(R.id.tvCurrentFavoriteStatus)
        btnFavorite = findViewById(R.id.btnFavorite)
        btnOpenMaps = findViewById(R.id.btnOpenMaps)
        btnOpenBrowser = findViewById(R.id.btnOpenBrowser)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set judul di CollapsingToolbarLayout
        collapsingToolbar.title = heritageItem.name
        collapsingToolbar.setExpandedTitleColor(getColor(android.R.color.white))
        collapsingToolbar.setCollapsedTitleTextColor(getColor(android.R.color.white))

        toolbarDetail.setNavigationOnClickListener {
            // Kirim data balik sebelum finish
            sendResultBack()
        }
    }

    /**
     * Bind data HeritageItem ke semua view
     */
    private fun bindData() {
        tvDetailName.text = heritageItem.name
        tvDetailCategory.text = heritageItem.category
        tvDetailOrigin.text = heritageItem.origin
        tvDetailYear.text = "${heritageItem.year} M"

        // Tampilkan rarity dengan warna berbeda menggunakan when (Control Flow)
        val rarity = heritageItem.getRarityLevel()
        tvDetailRarity.text = rarity

        val rarityBgRes = when (rarity) {
            "Sangat Langka" -> R.drawable.bg_chip_very_rare
            "Langka" -> R.drawable.bg_chip_rare
            else -> R.drawable.bg_chip_protected
        }
        tvDetailRarity.setBackgroundResource(rarityBgRes)

        tvDetailDescription.text = heritageItem.description
        tvCurrentFavoriteStatus.text = "Belum ditandai sebagai favorit"

        // Load hero image
        val imageSource: Any = if (heritageItem.imageUrl.isNotEmpty()) {
            heritageItem.imageUrl
        } else {
            heritageItem.imageResId
        }

        Glide.with(this)
            .load(imageSource)
            .placeholder(R.drawable.img_heritage_placeholder)
            .error(R.drawable.img_heritage_placeholder)
            .centerCrop()
            .into(imgDetailHero)
    }

    private fun setupClickListeners() {
        // Tombol Tandai Favorit
        btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteUI()
        }

        // Tombol Google Maps - Implicit Intent
        btnOpenMaps.setOnClickListener {
            openGoogleMaps()
        }

        // Tombol Browser - Implicit Intent
        btnOpenBrowser.setOnClickListener {
            openBrowser()
        }
    }

    /**
     * Update tampilan tombol favorit dan status
     */
    private fun updateFavoriteUI() {
        if (isFavorite) {
            btnFavorite.text = "★ Sudah Favorit"
            btnFavorite.setBackgroundColor(getColor(R.color.color_favorite))
            tvCurrentFavoriteStatus.text = "✅ Ditandai sebagai favorit!"
            tvCurrentFavoriteStatus.setTextColor(getColor(R.color.color_favorite))
            Toast.makeText(this, "❤️ ${heritageItem.name} ditambahkan ke favorit!", Toast.LENGTH_SHORT).show()
        } else {
            btnFavorite.text = "★ Tandai Favorit"
            btnFavorite.setBackgroundColor(getColor(R.color.color_not_favorite))
            tvCurrentFavoriteStatus.text = "Belum ditandai sebagai favorit"
            tvCurrentFavoriteStatus.setTextColor(getColor(R.color.color_text_secondary))
            Toast.makeText(this, "🤍 ${heritageItem.name} dihapus dari favorit", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Implicit Intent: Membuka lokasi di Google Maps
     */
    private fun openGoogleMaps() {
        val query = Uri.encode(heritageItem.location)
        val mapsUri = Uri.parse("geo:0,0?q=$query")
        val mapsIntent = Intent(Intent.ACTION_VIEW, mapsUri).apply {
            setPackage("com.google.android.apps.maps")
        }

        // Cek apakah Google Maps tersedia, jika tidak gunakan browser
        if (mapsIntent.resolveActivity(packageManager) != null) {
            startActivity(mapsIntent)
        } else {
            // Fallback: buka di browser
            val browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$query")
            val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
            startActivity(browserIntent)
        }
    }

    /**
     * Implicit Intent: Membuka artikel di Browser (Wikipedia)
     */
    private fun openBrowser() {
        val url = heritageItem.getWikipediaUrl()
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    /**
     * Kirim data balik (status favorit) ke MainActivity dan tutup Activity
     */
    private fun sendResultBack() {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_IS_FAVORITE, isFavorite)
            putExtra(EXTRA_ITEM_NAME, heritageItem.name)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    /**
     * Override onBackPressed agar tombol Back juga mengirim data balik
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        sendResultBack()
    }
}
