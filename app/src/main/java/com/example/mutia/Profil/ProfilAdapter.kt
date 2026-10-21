package com.example.mutia.Profil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.mutia.databinding.ItemProfilBinding
import com.google.android.material.snackbar.Snackbar

class ProfilAdapter(
    private val context: Context,
    private val messages: List<ProfilModel>
) : ArrayAdapter<ProfilModel>(context, 0, messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemProfilBinding
        val view: View

        if (convertView == null) {
            binding = ItemProfilBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemProfilBinding
        }

        val data = messages[position]

        // Properti disesuaikan dengan ProfilModel
        binding.textSender.text = data.title
        binding.textMessage.text = data.desc

        Glide.with(context)
            .load(data.imageUrl)
            .into(binding.avatarImg)

        view.setOnClickListener {
            Snackbar.make(
                parent,
                "Profil: ${data.title}",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return view
    }
}