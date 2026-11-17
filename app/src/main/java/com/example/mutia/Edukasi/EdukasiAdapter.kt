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

    // ViewHolder: menyimpan binding dari item_edukasi.xml untuk satu baris
    inner class EdukasiViewHolder(val binding: ItemEdukasiBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Dipanggil saat baris baru perlu dibuat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EdukasiViewHolder {
        val binding = ItemEdukasiBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EdukasiViewHolder(binding)
    }

    // Dipanggil setiap baris perlu diisi data (bisa berkali-kali karena baris didaur ulang)
    override fun onBindViewHolder(holder: EdukasiViewHolder, position: Int) {
        val item = itemList[position]
        with(holder.binding) {
            tvEdukasiTitle.text = item.title
            tvEdukasiSubtitle.text = item.subtitle

            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .placeholder(ColorDrawable(Color.parseColor("#F3EEF8")))
                .into(imgEdukasi)

            root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun getItemCount(): Int = itemList.size
}
