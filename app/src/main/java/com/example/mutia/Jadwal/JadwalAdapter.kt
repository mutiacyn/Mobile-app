// LOKASI: package Jadwal -> JadwalAdapter.kt
// FUNGSI: Custom Adapter ListView pengingat (seperti MessageAdapter di modul)
package com.example.mutia.Jadwal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mutia.databinding.ItemJadwalBinding
import com.google.android.material.snackbar.Snackbar

class JadwalAdapter(
    context: Context,
    private val jadwals: List<JadwalModel>,
    private val onLongClick: ((JadwalModel) -> Unit)? = null
) : ArrayAdapter<JadwalModel>(context, 0, jadwals) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemJadwalBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root
        val data = jadwals[position]

        // Isi data ke layout
        binding.iconImg.setImageResource(data.iconRes)
        binding.textTitle.text = data.title
        binding.textWaktu.text = data.waktu
        binding.textStatus.text = data.status

        // Warna kotak ikon + warna ikon berdasarkan kategori
        val (iconBg, iconTint) = when (data.kategori) {
            "Rutinitas" -> Pair("#FFEFE0", "#E07B39")
            "Imunisasi" -> Pair("#E3F6EE", "#2E9E75")
            else -> Pair("#F8E6EE", "#8A4B5E")
        }
        binding.iconImg.background = shapeBg(context, Color.parseColor(iconBg), 14f)
        binding.iconImg.imageTintList = ColorStateList.valueOf(Color.parseColor(iconTint))

        // Warna badge status
        val (textColor, bgColor) = when (data.status) {
            "Terjadwal" -> Pair("#1B8A3E", "#DFF5E5")
            "Mendatang" -> Pair("#D9570B", "#FFE8D6")
            else -> Pair("#1C64D8", "#DCEAFE")
        }
        binding.textStatus.setTextColor(Color.parseColor(textColor))
        binding.textStatus.background = shapeBg(context, Color.parseColor(bgColor), 10f)

        // Aksi klik dipasang di adapter, tepat sebelum return view
        view.setOnClickListener {
            Snackbar.make(parent, "${data.title}: ${data.waktu}", Snackbar.LENGTH_SHORT).show()
        }
        view.setOnLongClickListener {
            onLongClick?.invoke(data)
            true
        }
        return view
    }
}
