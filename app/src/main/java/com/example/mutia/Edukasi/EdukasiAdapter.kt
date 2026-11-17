package com.example.mutia.Edukasi

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mutia.databinding.ItemEdukasiBinding

class EdukasiAdapter(
    private val itemList: List<EdukasiModel>,
    private val onItemClick: (EdukasiModel) -> Unit
) : RecyclerView.Adapter<EdukasiAdapter.EdukasiViewHolder>() {

    // Warna latar lembut bergantian agar tampilan lebih hidup
    private val warnaLatar = listOf("#FCE4EC", "#F3E5F5", "#E8F5E9", "#FFF3E0", "#E3F2FD")

    // ViewHolder: menyimpan binding dari item_edukasi.xml untuk satu baris
    inner class EdukasiViewHolder(val binding: ItemEdukasiBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EdukasiViewHolder {
        val binding = ItemEdukasiBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EdukasiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EdukasiViewHolder, position: Int) {
        val item = itemList[position]
        with(holder.binding) {
            tvEdukasiTitle.text = item.title
            tvEdukasiSubtitle.text = item.subtitle
            tvEdukasiEmoji.text = item.emoji
            layoutGambar.setBackgroundColor(
                Color.parseColor(warnaLatar[position % warnaLatar.size])
            )

            root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun getItemCount(): Int = itemList.size
}
