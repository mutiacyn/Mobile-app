package com.example.mutia.Edukasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mutia.databinding.FragmentEdukasiTipsBinding

class EdukasiTipsFragment : Fragment() {

    private var _binding: FragmentEdukasiTipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEdukasiTipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = EdukasiAdapter(EdukasiData.tips) { selected ->
            Toast.makeText(
                requireContext(),
                "Anda memilih ${selected.title}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvEdukasi.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
