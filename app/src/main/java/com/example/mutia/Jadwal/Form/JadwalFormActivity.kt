package com.example.mutia.Jadwal.Form

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mutia.Data.AppDatabase
import com.example.mutia.Data.Entity.JadwalEntity
import com.example.mutia.R
import com.example.mutia.databinding.ActivityJadwalFormBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class JadwalFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJadwalFormBinding
    private lateinit var db: AppDatabase

    private val kategoriList = listOf("Pemeriksaan", "Rutinitas", "Imunisasi")
    private val statusList = listOf("Terjadwal", "Mendatang", "Belum dilakukan")

    // MaterialDatePicker bekerja dengan waktu UTC, jadi formatter ini juga UTC
    private val utcKeyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    private val utcLabelFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID")).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    private var dateKey: String = ""   // yyyy-MM-dd
    private var jam: String = ""       // HH:mm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJadwalFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Tambah Jadwal"
            subtitle = "MomCare"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }

        db = AppDatabase.getInstance(this)

        // Dropdown kategori & status (nilai awal = pilihan pertama)
        binding.actKategori.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_list_item_1, kategoriList)
        )
        binding.actKategori.setText(kategoriList[0], false)
        binding.actStatus.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_list_item_1, statusList)
        )
        binding.actStatus.setText(statusList[0], false)

        // Tanggal awal = tanggal yang sedang dipilih di kalender JadwalFragment
        intent.getStringExtra(EXTRA_DATE)?.let {
            dateKey = it
            tampilkanTanggal()
        }

        binding.etTanggal.setOnClickListener { bukaDatePicker() }
        binding.etJam.setOnClickListener { bukaTimePicker() }

        binding.btnSimpanJadwal.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            if (title.isBlank() || dateKey.isBlank() || jam.isBlank()) {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val kategori = binding.actKategori.text.toString()
            val status = binding.actStatus.text.toString()

            binding.btnSimpanJadwal.isEnabled = false
            lifecycleScope.launch {
                try {
                    db.jadwalDao().insert(
                        JadwalEntity(
                            title = title,
                            dateKey = dateKey,
                            jam = jam,
                            kategori = kategori,
                            status = status
                        )
                    )
                    Toast.makeText(this@JadwalFormActivity, "Jadwal tersimpan", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    binding.btnSimpanJadwal.isEnabled = true
                    Toast.makeText(this@JadwalFormActivity, "Gagal menyimpan jadwal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun tampilkanTanggal() {
        try {
            binding.etTanggal.setText(utcLabelFormat.format(utcKeyFormat.parse(dateKey)!!))
        } catch (e: Exception) {
            binding.etTanggal.setText(dateKey)
        }
    }

    private fun bukaDatePicker() {
        val awal = try {
            utcKeyFormat.parse(dateKey)?.time
        } catch (e: Exception) {
            null
        } ?: MaterialDatePicker.todayInUtcMilliseconds()

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih tanggal")
            .setSelection(awal)
            .build()
        picker.addOnPositiveButtonClickListener { millis ->
            dateKey = utcKeyFormat.format(Date(millis))
            tampilkanTanggal()
        }
        picker.show(supportFragmentManager, "date_picker")
    }

    private fun bukaTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(9)
            .setMinute(0)
            .setTitleText("Pilih jam")
            .build()
        picker.addOnPositiveButtonClickListener {
            jam = String.format(Locale.US, "%02d:%02d", picker.hour, picker.minute)
            binding.etJam.setText(jam.replace(':', '.') + " WIB")
        }
        picker.show(supportFragmentManager, "time_picker")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_DATE = "extra_date"
    }
}