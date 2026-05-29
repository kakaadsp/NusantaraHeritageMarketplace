package com.upn.nusantaraheritagemarketplace

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MainActivity - Halaman Utama Aplikasi
 *
 * Fitur:
 * - Menampilkan daftar 10 warisan budaya menggunakan RecyclerView
 * - Toggle tampilan antara LinearLayoutManager (List) dan GridLayoutManager (Grid 2 kolom)
 * - Navigasi dua arah: membuka DetailActivity dan menerima data balik (status favorit)
 * - Menggunakan registerForActivityResult untuk menangkap nilai balik
 */
class MainActivity : AppCompatActivity(), OnHeritageClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HeritageAdapter
    private lateinit var tvFavoriteStatus: TextView
    private lateinit var toolbar: Toolbar

    private var isGridMode = false
    private val heritageList = mutableListOf<HeritageItem>()

    // =========================================================
    // registerForActivityResult: Menangkap data balik dari Detail
    // =========================================================
    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val isFavorite = data?.getBooleanExtra(DetailActivity.EXTRA_IS_FAVORITE, false) ?: false
            val itemName = data?.getStringExtra(DetailActivity.EXTRA_ITEM_NAME) ?: ""

            // Update tampilan status favorit di halaman utama
            val statusText = if (isFavorite) "Ya ❤️" else "Tidak 🤍"
            tvFavoriteStatus.text = "$statusText — $itemName"
            tvFavoriteStatus.setTextColor(
                if (isFavorite)
                    getColor(R.color.color_favorite)
                else
                    getColor(R.color.color_text_secondary)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Init Views
        recyclerView = findViewById(R.id.recyclerView)
        tvFavoriteStatus = findViewById(R.id.tvFavoriteStatus)

        // Muat data warisan budaya dari resources
        loadHeritageData()

        // Setup RecyclerView dengan LinearLayoutManager (default: List Vertikal)
        setupRecyclerView()
    }

    /**
     * Memuat data 10 warisan budaya dari string-array dan integer-array di strings.xml
     */
    private fun loadHeritageData() {
        val names = resources.getStringArray(R.array.heritage_names)
        val origins = resources.getStringArray(R.array.heritage_origins)
        val categories = resources.getStringArray(R.array.heritage_categories)
        val years = resources.getStringArray(R.array.heritage_years)
        val descriptions = resources.getStringArray(R.array.heritage_descriptions)
        val locations = resources.getStringArray(R.array.heritage_locations)

        // Integer-array untuk drawable resource gambar (dari strings.xml)
        val imageResIds = resources.obtainTypedArray(R.array.heritage_images)

        // URL gambar warisan budaya (menggunakan Wikimedia Commons)
        val imageUrls = listOf(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Borobudur-temple-4-Asienreisender.jpg/640px-Borobudur-temple-4-Asienreisender.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Batik_Indonesia.jpg/640px-Batik_Indonesia.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/Wayang_Kulit_by_Tropenmuseum.jpg/640px-Wayang_Kulit_by_Tropenmuseum.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/3/33/KecakDanceAtUluwatu.jpg/640px-KecakDanceAtUluwatu.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Keris_from_Java.jpg/640px-Keris_from_Java.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Rumah_Gadang_in_West_Sumatra.jpg/640px-Rumah_Gadang_in_West_Sumatra.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Gong-Kemodong-gamelan.jpg/640px-Gong-Kemodong-gamelan.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Tenun_Ikat_NTT.jpg/640px-Tenun_Ikat_NTT.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Saman_dance.jpg/640px-Saman_dance.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Tongkonan_Toraja_Architecture.jpg/640px-Tongkonan_Toraja_Architecture.jpg"
        )

        heritageList.clear()
        for (i in names.indices) {
            val resId = imageResIds.getResourceId(i, R.drawable.img_heritage_placeholder)
            heritageList.add(
                HeritageItem(
                    id = i + 1,
                    name = names[i],
                    origin = origins[i],
                    category = categories[i],
                    year = years[i].toInt(),
                    description = descriptions[i],
                    imageResId = resId,
                    imageUrl = imageUrls[i],
                    location = locations[i]
                )
            )
        }

        // Recycle TypedArray untuk menghindari memory leak
        imageResIds.recycle()
    }

    /**
     * Inisialisasi RecyclerView dengan adapter dan LinearLayoutManager
     */
    private fun setupRecyclerView() {
        adapter = HeritageAdapter(heritageList, this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            setHasFixedSize(true)
        }
    }

    /**
     * Toggle tampilan antara List dan Grid menggunakan LayoutManager
     */
    private fun toggleLayoutManager(menuItem: MenuItem) {
        isGridMode = !isGridMode

        if (isGridMode) {
            // Switch ke GridLayoutManager (2 kolom)
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            adapter.viewType = HeritageAdapter.VIEW_TYPE_GRID
            menuItem.setIcon(R.drawable.ic_list_view)
            menuItem.title = getString(R.string.switch_to_list)
            Toast.makeText(this, "🔲 Tampilan Grid Aktif", Toast.LENGTH_SHORT).show()
        } else {
            // Switch ke LinearLayoutManager (List Vertikal)
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter.viewType = HeritageAdapter.VIEW_TYPE_LIST
            menuItem.setIcon(R.drawable.ic_grid_view)
            menuItem.title = getString(R.string.switch_to_grid)
            Toast.makeText(this, "📋 Tampilan List Aktif", Toast.LENGTH_SHORT).show()
        }
    }

    // =========================================================
    // OnHeritageClickListener Implementation
    // =========================================================
    override fun onHeritageClick(item: HeritageItem, position: Int) {
        // Tampilkan Toast berisi informasi kategori (sesuai spesifikasi)
        Toast.makeText(
            this,
            "📌 ${item.getCategoryDetails()}",
            Toast.LENGTH_LONG
        ).show()

        // Buka DetailActivity menggunakan Explicit Intent
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_HERITAGE_ITEM, item)
        }

        // Gunakan detailLauncher (registerForActivityResult) untuk navigasi dua arah
        detailLauncher.launch(intent)
    }

    // =========================================================
    // Menu Handling
    // =========================================================
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_toggle_layout -> {
                toggleLayoutManager(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}