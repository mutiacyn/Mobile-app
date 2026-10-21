package com.example.mutia.Kesehatan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mutia.R
import com.example.mutia.databinding.FragmentProfilBinding

class KesehatanFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val dataList = listOf(
        "Notifikasi & Pengingat", "Keamanan Akun & PIN", "Pengaturan Aplikasi & Bahasa",
        "Pusat Bantuan & Tanya Bidan"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kesehatan, container, false)
    }
}