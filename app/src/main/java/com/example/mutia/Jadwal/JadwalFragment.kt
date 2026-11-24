// LOKASI: package Jadwal -> JadwalFragment.kt
// FUNGSI: halaman Jadwal: Toolbar, kalender (diisi lewat kode), daftar pengingat, tombol Tambah
package com.example.mutia.Jadwal

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mutia.Data.AppDatabase
import com.example.mutia.Data.Entity.toModel
import com.example.mutia.Jadwal.Form.JadwalFormActivity
import com.example.mutia.R
import com.example.mutia.databinding.FragmentJadwalBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class JadwalFragment : Fragment() {

    private var _binding: FragmentJadwalBinding? = null
    private val binding get() = _binding!!

    private val keyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val monthFormat = SimpleDateFormat("MMMM yyyy", Locale("id", "ID"))

    private val today = Calendar.getInstance()
    private val bulanTampil = Calendar.getInstance()          // bulan yang sedang ditampilkan
    private var tanggalDipilih = keyFormat.format(today.time)  // default: hari ini

    private lateinit var db: AppDatabase
    private val jadwalList = mutableListOf<JadwalModel>()
    // Data jadwal (seperti messageList di MessageFragment), ganti dengan datamu sendiri
//    private val jadwalList = listOf(
//        JadwalModel(
//            "Pemeriksaan Kehamilan", "2026-11-10", "10 November 2026 • 09.00 WIB",
//            "Terjadwal", "Pemeriksaan", R.drawable.ic_health
//        ),
//        JadwalModel(
//            "Pengingat Konsumsi Vitamin", "2026-11-12", "12 November 2026 • 08.00 WIB",
//            "Mendatang", "Rutinitas", R.drawable.ic_health
//        ),
//        JadwalModel(
//            "Pemeriksaan USG", "2026-11-20", "20 November 2026 • 10.00 WIB",
//            "Belum dilakukan", "Imunisasi", R.drawable.ic_health
//        )
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJadwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar: judul, subjudul, dan logo (tanpa tombol kembali)
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.apply {
            title = " Jadwal"
            subtitle = " MomCare"
            setDisplayHomeAsUpEnabled(false)   // tidak ada ikon kembali
            setDisplayShowHomeEnabled(true)    // dibutuhkan supaya logo tampil
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }

        val ctx = requireContext()
        db = AppDatabase.getInstance(ctx)

        // Bentuk badge "Bulan Ini" dan titik legenda
        binding.tvBulanIni.background = shapeBg(ctx, Color.parseColor("#F8E6EE"), 8f)
        binding.dotLegend1.background = shapeBg(ctx, Color.parseColor("#8A4B5E"), oval = true)
        binding.dotLegend2.background = shapeBg(ctx, Color.parseColor("#6BC9A0"), oval = true)
        binding.dotLegend3.background = shapeBg(ctx, Color.parseColor("#F4A261"), oval = true)

        // ---------- Kalender ----------
        bulanTampil.set(Calendar.DAY_OF_MONTH, 1)
        renderKalender()

        binding.btnBulanSebelumnya.setOnClickListener {
            bulanTampil.add(Calendar.MONTH, -1)
            renderKalender()
        }
        binding.btnBulanBerikutnya.setOnClickListener {
            bulanTampil.add(Calendar.MONTH, 1)
            renderKalender()
        }

        // ---------- ListView pengingat (Custom Adapter) ----------
//        muatDaftar()
        binding.listViewJadwal.setOnItemLongClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position) as JadwalModel
            konfirmasiHapus(item)
            true
        }


        // ---------- ChipGroup Filter ----------
//        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
//            val checkedId = checkedIds.firstOrNull() ?: R.id.chipSemua
//            val todayKey = keyFormat.format(today.time)
//            val mendatang = jadwalList.filter { it.dateKey >= todayKey }.sortedBy { it.dateKey }
//            val filtered = when (checkedId) {
//                R.id.chipPemeriksaan -> mendatang.filter { it.kategori == "Pemeriksaan" }
//                R.id.chipRutinitas -> mendatang.filter { it.kategori == "Rutinitas" }
//                R.id.chipImunisasi -> mendatang.filter { it.kategori == "Imunisasi" }
//                else -> mendatang // Semua
//            }
//            binding.listViewJadwal.adapter = JadwalAdapter(requireContext(), filtered)
//            binding.tvJumlahPengingat.text = "${filtered.size} Jadwal"
//            binding.tvJadwalKosong.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
//            aturTinggiListView()
//        }
        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, _ ->
            terapkanFilter()                                   // [ROOM] logika dipindah ke fungsi
        }

        // ---------- Tombol Tambah Jadwal ----------
        binding.btnTambahJadwal.setOnClickListener {
//            Toast.makeText(ctx, "Tambah jadwal", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), JadwalFormActivity::class.java)
            intent.putExtra(JadwalFormActivity.EXTRA_DATE, tanggalDipilih)
            startActivity(intent)
        }
    }
    // [ROOM] Muat ulang data setiap halaman tampil (termasuk setelah form ditutup)
    override fun onResume() {
        super.onResume()
        if (_binding != null) muatDariDatabase()
    }
    // [ROOM] Baca semua jadwal dari SQLite, lalu segarkan kalender & daftar
    private fun muatDariDatabase() {
        viewLifecycleOwner.lifecycleScope.launch {
            val hasil = db.jadwalDao().getAll()
            jadwalList.clear()
            jadwalList.addAll(hasil.map { it.toModel() })
            renderKalender()
            terapkanFilter()
        }
    }
    private fun konfirmasiHapus(item: JadwalModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hapus Jadwal")
            .setMessage("Yakin ingin menghapus \"${item.title}\"?")
            .setPositiveButton("Ya") { _, _ -> hapusJadwal(item) }
            .setNegativeButton("Batal", null)
            .show()
    }
    private fun hapusJadwal(item: JadwalModel) {
        if (_binding == null) return
        viewLifecycleOwner.lifecycleScope.launch {
            db.jadwalDao().deleteById(item.id)
            muatDariDatabase()
        }
    }
    // [ROOM] Isi ListView sesuai chip yang sedang aktif (isi lama dari listener chip + muatDaftar)
    private fun terapkanFilter() {
        val todayKey = keyFormat.format(today.time)
        val mendatang = jadwalList.filter { it.dateKey >= todayKey }.sortedBy { it.dateKey }
        val filtered = when (binding.chipGroupFilter.checkedChipId) {
            R.id.chipPemeriksaan -> mendatang.filter { it.kategori == "Pemeriksaan" }
            R.id.chipRutinitas -> mendatang.filter { it.kategori == "Rutinitas" }
            R.id.chipImunisasi -> mendatang.filter { it.kategori == "Imunisasi" }
            else -> mendatang // Semua
        }
        binding.listViewJadwal.adapter = JadwalAdapter(requireContext(), filtered)
        binding.tvJumlahPengingat.text = "${filtered.size} Jadwal"
        binding.tvJadwalKosong.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        aturTinggiListView()
    }

    // Gambar ulang kalender: 42 sel (6 baris x 7 kolom), minggu dimulai hari Senin
    private fun renderKalender() {
        binding.tvBulanTahun.text = monthFormat.format(bulanTampil.time)
            .replaceFirstChar { it.uppercase() }

        val bulanIni = bulanTampil.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                bulanTampil.get(Calendar.YEAR) == today.get(Calendar.YEAR)
        binding.tvBulanIni.visibility = if (bulanIni) View.VISIBLE else View.GONE

        val grid = binding.gridKalender
        grid.removeAllViews()

        val cal = bulanTampil.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val offset = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7  // Senin = 0
        cal.add(Calendar.DAY_OF_MONTH, -offset)

        repeat(42) {
            val dalamBulan = cal.get(Calendar.MONTH) == bulanTampil.get(Calendar.MONTH)
            grid.addView(
                buatSelHari(cal.get(Calendar.DAY_OF_MONTH), dalamBulan, keyFormat.format(cal.time))
            )
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    // Membuat satu sel tanggal (angka + titik penanda jadwal)
    private fun buatSelHari(day: Int, dalamBulan: Boolean, key: String): View {
        val ctx = requireContext()
        val terpilih = key == tanggalDipilih
        val hariIni = key == keyFormat.format(today.time)

        val sel = FrameLayout(ctx)
        val lp = GridLayout.LayoutParams(
            GridLayout.spec(GridLayout.UNDEFINED),
            GridLayout.spec(GridLayout.UNDEFINED, 1f)
        )
        lp.width = 0
        lp.height = dp(46)
        lp.setGravity(Gravity.FILL_HORIZONTAL)
        sel.layoutParams = lp

        val tv = TextView(ctx)
        tv.text = day.toString()
        tv.gravity = Gravity.CENTER
        tv.textSize = 14f
        tv.layoutParams = FrameLayout.LayoutParams(dp(36), dp(36), Gravity.CENTER)

        when {
            terpilih -> {
                tv.background = shapeBg(ctx, Color.parseColor("#8A4B5E"), oval = true)
                tv.setTextColor(Color.WHITE)
                tv.setTypeface(null, Typeface.BOLD)
            }
            !dalamBulan -> tv.setTextColor(Color.parseColor("#CFC6CC"))
            hariIni -> {
                tv.setTextColor(Color.parseColor("#8A4B5E"))
                tv.setTypeface(null, Typeface.BOLD)
            }
            else -> tv.setTextColor(Color.parseColor("#33252D"))
        }
        sel.addView(tv)

        // Titik penanda kalau ada jadwal di tanggal itu
        val jadwalHariItu = jadwalList.filter { it.dateKey == key }
        if (jadwalHariItu.isNotEmpty() && dalamBulan) {
            val dot = View(ctx)
            val dlp = FrameLayout.LayoutParams(dp(5), dp(5), Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
            dlp.bottomMargin = dp(1)
            dot.layoutParams = dlp
            val warna = if (terpilih) Color.parseColor("#8A4B5E")
            else kategoriDotColor(jadwalHariItu[0].kategori)
            dot.background = shapeBg(ctx, warna, oval = true)
            sel.addView(dot)
        }

        sel.setOnClickListener {
            tanggalDipilih = key
            renderKalender()
            val pesan = if (jadwalHariItu.isEmpty()) "Tidak ada jadwal di tanggal ini"
            else jadwalHariItu.joinToString { it.title }
            Toast.makeText(ctx, pesan, Toast.LENGTH_SHORT).show()
        }
        return sel
    }

    // Isi ListView dengan pengingat mendatang (tanggal >= hari ini, urut tanggal)
    private fun muatDaftar() {
        val todayKey = keyFormat.format(today.time)
        val mendatang = jadwalList.filter { it.dateKey >= todayKey }.sortedBy { it.dateKey }

        binding.listViewJadwal.adapter = JadwalAdapter(requireContext(), mendatang)
        binding.tvJumlahPengingat.text = "${mendatang.size} Jadwal"
        binding.tvJadwalKosong.visibility = if (mendatang.isEmpty()) View.VISIBLE else View.GONE

        aturTinggiListView()
    }

    // ListView di dalam NestedScrollView hanya menampilkan 1 baris kalau tinggi wrap_content,
    // jadi tingginya dihitung manual dari semua item
    private fun aturTinggiListView() {
        val lv = binding.listViewJadwal
        lv.post {
            if (_binding == null) return@post
            val adapter = lv.adapter ?: return@post
            val lebar = if (lv.width > 0) lv.width
            else resources.displayMetrics.widthPixels - dp(32)
            val widthSpec = View.MeasureSpec.makeMeasureSpec(lebar, View.MeasureSpec.EXACTLY)
            val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

            var total = 0
            for (i in 0 until adapter.count) {
                val item = adapter.getView(i, null, lv)
                item.measure(widthSpec, heightSpec)
                total += item.measuredHeight
            }
            total += lv.dividerHeight * (adapter.count - 1).coerceAtLeast(0)

            val params = lv.layoutParams
            params.height = total
            lv.layoutParams = params
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}