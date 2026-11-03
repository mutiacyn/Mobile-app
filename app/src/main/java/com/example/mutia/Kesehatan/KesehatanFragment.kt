package com.example.mutia.Kesehatan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mutia.R
import com.example.mutia.databinding.FragmentKesehatanBinding

class KesehatanFragment : Fragment() {

    private var _binding: FragmentKesehatanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKesehatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)

        activity.supportActionBar?.apply {
            title = "Kesehatan"
            subtitle = "MomCare"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }

        binding.btnTambahData.setOnClickListener {
            Toast.makeText(requireContext(), "+ Tambah Data Kesehatan", Toast.LENGTH_SHORT).show()
        }

        binding.tvRiwayatDetail.setOnClickListener {
            Toast.makeText(requireContext(), "Riwayat Detail", Toast.LENGTH_SHORT).show()
        }

        binding.tvSemuaCatatan.setOnClickListener {
            Toast.makeText(requireContext(), "Semua Catatan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
