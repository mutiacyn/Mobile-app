package com.example.mutia.Beranda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.mutia.BaseActivity
import com.example.mutia.DetailActivity
import com.example.mutia.R
import com.example.mutia.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnKesehatanIbu.setOnClickListener {
            (requireActivity() as? BaseActivity)?.navigateToTab(R.id.health)
        }
        binding.btnKalender.setOnClickListener {
            (requireActivity() as? BaseActivity)?.navigateToTab(R.id.jadwal)
        }
        binding.btnArtikelKesehatan.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
        }
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)

        // 3. Konfigurasi Judul, Subtitle, Logo, dan Navigation Icon
        super.onViewCreated(view, savedInstanceState)

        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.apply {
            title = " Beranda"
            subtitle = " MomCare"
            setDisplayHomeAsUpEnabled(false)   // tidak ada ikon kembali
            setDisplayShowHomeEnabled(true)    // dibutuhkan supaya logo tampil
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}