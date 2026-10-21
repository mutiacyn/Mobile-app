package com.example.mutia.Jadwal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mutia.R
import com.example.mutia.databinding.FragmentJadwalBinding

class JadwalFragment : Fragment() {

    private var _binding: FragmentJadwalBinding? = null
    private val binding get() = _binding!!

    // Data Dummy Jadwal Kegiatan Ibu
    private val dummyJadwalList = listOf(
        "08:00 WIB - Minum Vitamin C & Kalsium",
        "10:00 WIB - Konsultasi Rutin Trimester 3 (Klinik Permata Ibu)",
        "16:00 WIB - Senam Hamil Ringan (30 Menit)",
        "20:00 WIB - Minum Susu Ibu Hamil & Suplemen Zat Besi"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJadwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Konfigurasi Toolbar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.apply {
            title = "Jadwal"
            subtitle = "MomCare"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }

        // 2. Set Adapter Dummy ke ListView
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dummyJadwalList
        )
        binding.listJadwal.adapter = adapter

        // 3. Event Listener Klik Tanggal Kalender
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            binding.tvSelectedDateHeader.text = "Agenda Tanggal: $selectedDate"
            Toast.makeText(
                requireContext(),
                "Menampilkan agenda tanggal $selectedDate",
                Toast.LENGTH_SHORT
            ).show()
        }

        // 4. Event Listener Klik Item Agenda
        binding.listJadwal.setOnItemClickListener { _, _, position, _ ->
            val selectedAgenda = dummyJadwalList[position]
            Toast.makeText(
                requireContext(),
                "Agenda: $selectedAgenda",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}