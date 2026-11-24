package com.example.mutia.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mutia.Jadwal.JadwalModel
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.mutia.R

@Entity(tableName = "jadwal")
data class JadwalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val dateKey: String,   // yyyy-MM-dd
    val jam: String,       // HH:mm
    val kategori: String,  // Pemeriksaan / Rutinitas / Imunisasi
    val status: String,    // Terjadwal / Mendatang / Belum dilakukan
    val createdAt: Long = System.currentTimeMillis()
)

// Mengubah baris database menjadi JadwalModel yang dipakai adapter & kalender
fun JadwalEntity.toModel(): JadwalModel {
    val tanggal = try {
        val parsed = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateKey)
        SimpleDateFormat("d MMMM yyyy", Locale("id", "ID")).format(parsed!!)
    } catch (e: Exception) {
        dateKey
    }
    return JadwalModel(
        title = title,
        dateKey = dateKey,
        waktu = "$tanggal • ${jam.replace(':', '.')} WIB",
        status = status,
        kategori = kategori,
        iconRes = R.drawable.ic_health,
        id = id
    )
}