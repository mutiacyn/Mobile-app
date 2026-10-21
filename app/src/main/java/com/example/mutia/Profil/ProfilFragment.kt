package com.example.mutia.Profil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mutia.R
import com.example.mutia.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!


    private val profilList = listOf(
        ProfilModel(
            title = "Ibu Mutia Ayu Pramesti",
            desc = "mutia.pramesti@gmail.com\n+62 812-3456-7890",
            imageUrl = "https://avatar.iran.liara.run/public/65"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
//        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Halaman More"

        val activity = requireActivity() as AppCompatActivity
        binding.listMessageItems.adapter = adapter
        activity.setSupportActionBar(binding.toolbar)

        // 3. Konfigurasi Judul, Subtitle, Logo, dan Navigation Icon
        activity.supportActionBar?.apply {
            title = "Profil"
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