package com.example.mutia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.mutia.Beranda.BerandaFragment
import com.example.mutia.Jadwal.JadwalFragment
import com.example.mutia.Kesehatan.KesehatanFragment
import com.example.mutia.Profil.ProfilFragment
import com.example.mutia.databinding.ActivityBaseBinding
import com.example.mutia.databinding.ActivityDetailBinding
import kotlin.text.replace

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        replaceFragment(BerandaFragment())
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> {
                    replaceFragment(BerandaFragment())
                    true
                }

                R.id.health -> {
                    replaceFragment(KesehatanFragment())
                    true
                }

                R.id.jadwal -> {
                    replaceFragment(JadwalFragment())
                    true
                }

                R.id.profil -> {
                    replaceFragment(ProfilFragment())
                    true
                }

                else -> false // false jika item tidak dikenal
            }
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            // .addToBackStack(null) sengaja dinonaktifkan: saat back langsung keluar aplikasi
            .commit()
    }

    fun navigateToTab(itemId: Int) {
        binding.bottomNavView.selectedItemId = itemId
    }
}

