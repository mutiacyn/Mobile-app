package com.example.mutia.Edukasi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class EdukasiTabsAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    // Jumlah total tab (harus sama dengan jumlah case di createFragment)
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EdukasiTipsFragment()
            1 -> EdukasiNutrisiFragment()
            2 -> EdukasiAktivitasFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}