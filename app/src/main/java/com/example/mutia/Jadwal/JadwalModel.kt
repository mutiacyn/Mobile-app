// LOKASI: package Jadwal -> JadwalModel.kt
// FUNGSI: data class JadwalModel + fungsi bantu bentuk/warna (pengganti file drawable)
package com.example.mutia.Jadwal

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable

// Wadah data satu pengingat (seperti MessageModel di modul)
data class JadwalModel(
    val title: String,
    val dateKey: String,      // format yyyy-MM-dd
    val waktu: String,        // teks yang ditampilkan
    val status: String,       // Terjadwal / Mendatang / Belum dilakukan
    val kategori: String,     // Pemeriksaan / Rutinitas / Imunisasi
    val iconRes: Int
)

// Membuat bentuk (kotak membulat / lingkaran) lewat kode
fun shapeBg(context: Context, color: Int, radiusDp: Float = 0f, oval: Boolean = false): GradientDrawable {
    val d = GradientDrawable()
    d.shape = if (oval) GradientDrawable.OVAL else GradientDrawable.RECTANGLE
    d.setColor(color)
    d.cornerRadius = radiusDp * context.resources.displayMetrics.density
    return d
}

// Warna titik di kalender berdasarkan kategori
fun kategoriDotColor(kategori: String): Int = when (kategori) {
    "Imunisasi" -> Color.parseColor("#6BC9A0")
    "Rutinitas" -> Color.parseColor("#F4A261")
    else -> Color.parseColor("#8A4B5E")
}
