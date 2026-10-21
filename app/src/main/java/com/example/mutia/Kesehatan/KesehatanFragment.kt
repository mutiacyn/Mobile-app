package com.example.mutia.Kesehatan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mutia.R
import com.example.mutia.databinding.FragmentKesehatanBinding
import com.example.mutia.databinding.FragmentProfilBinding

class KesehatanFragment : Fragment() {

    private var _binding: FragmentKesehatanBinding? = null
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
        _binding = FragmentKesehatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
//        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Halaman More"

        val activity = requireActivity() as AppCompatActivity

        activity.setSupportActionBar(binding.toolbar)

        // 3. Konfigurasi Judul, Subtitle, Logo, dan Navigation Icon
        activity.supportActionBar?.apply {
            title = "Pengaturan"
            subtitle = "MomCare"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dataList
        )
        binding.listMessageItems.adapter = adapter
        binding.listMessageItems.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataList[position]
            Toast.makeText(
                requireContext(), "Kamu memilih: $selectedItem",
                Toast.LENGTH_SHORT
            ).show()

        }

    }
}