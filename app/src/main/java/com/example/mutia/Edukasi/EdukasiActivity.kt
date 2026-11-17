package com.example.mutia.Edukasi

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mutia.R
import com.example.mutia.databinding.ActivityEdukasiBinding
import com.google.android.material.tabs.TabLayoutMediator

class EdukasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdukasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEdukasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Edukasi Ibu Hamil"
            subtitle = "MomCare"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }
        // 1. Pasang adapter ke ViewPager2
        binding.viewPager.adapter = EdukasiTabsAdapter(this)

        // 2. Hubungkan TabLayout dan ViewPager2 (harus SETELAH adapter dipasang)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Tips"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_bell)
                    // Badge titik (tanpa angka)
                    val badge = tab.getOrCreateBadge()
                    badge.backgroundColor = Color.parseColor("#8A4B5E")
                    badge.isVisible = true
                }
                1 -> {
                    tab.text = "Nutrisi"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_apps)
                    // Badge angka = jumlah makanan
                    val badge = tab.getOrCreateBadge()
                    badge.backgroundColor = Color.parseColor("#8A4B5E")
                    badge.badgeTextColor = Color.WHITE
                    badge.number = EdukasiData.nutrisi.size
                    badge.isVisible = true
                }
                2 -> {
                    tab.text = "Aktivitas"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_calender)
                }
            }
        }.attach()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}