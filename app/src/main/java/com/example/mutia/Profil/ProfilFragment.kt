
package com.example.mutia.Profil

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mutia.DetailActivity
import com.example.mutia.LoginActivity
import com.example.mutia.R
import com.example.mutia.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    // Data menu Profil
    private val dataList = listOf(
        "Notifikasi & Pengingat",
        "Keamanan Akun & PIN",
        "Pengaturan Aplikasi & Bahasa",
        "Pusat Bantuan & Tanya Bidan",
        "Detail Aplikasi",
        "Keluar dari Akun"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfilBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ctx = requireContext()

        // Toolbar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)

        activity.supportActionBar?.apply {
            title = "Profil"
            subtitle = "MomCare"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }

        // Foto profil
        val urlFoto = "https://avatar.iran.liara.run/public/8"

        Glide.with(ctx)
            .load(urlFoto)
            .circleCrop()
            .into(binding.imgAvatar)

        Glide.with(ctx)
            .load(urlFoto)
            .circleCrop()
            .into(binding.imgAvatarToolbar)

        // Background komponen profil
        binding.tvBadgeVerifikasi.background = bentuk(
            Color.parseColor("#FBE4EC")
        )

        binding.tvCentang.background = bentuk(
            Color.parseColor("#2E9E75"),
            true
        )

        binding.btnEditProfil.background = bentuk(
            Color.parseColor("#F8E6EE"),
            true
        )

        // Tombol notifikasi
        binding.imgBell.setOnClickListener {
            Toast.makeText(
                ctx,
                "Notifikasi",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Tombol edit profil
        binding.btnEditProfil.setOnClickListener {
            Toast.makeText(
                ctx,
                "Edit profil",
                Toast.LENGTH_SHORT
            ).show()
        }

        // ArrayAdapter menggunakan simple_list_item_1
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dataList
        )

        binding.listViewMenu.adapter = adapter

        // Aksi ketika item ListView diklik
        binding.listViewMenu.setOnItemClickListener { _, _, position, _ ->

            val selectedItem = dataList[position]

            when (selectedItem) {

                "Detail Aplikasi" -> {
                    bukaDetailAplikasi()
                }

                "Keluar dari Akun" -> {
                    tampilkanDialogLogout()
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Kamu memilih: $selectedItem",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // Membuka halaman Detail Aplikasi
    private fun bukaDetailAplikasi() {
        val intent = Intent(
            requireContext(),
            DetailActivity::class.java
        )

        startActivity(intent)
    }

    // Dialog Logout
    private fun tampilkanDialogLogout() {

        val sharedPref = requireContext().getSharedPreferences(
            "user_pref",
            Context.MODE_PRIVATE
        )

        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { dialog, _ ->

                sharedPref.edit().clear().apply()

                dialog.dismiss()

                val intent = Intent(
                    requireContext(),
                    LoginActivity::class.java
                )

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // Membuat background komponen
    private fun bentuk(
        color: Int,
        oval: Boolean = false
    ): GradientDrawable {

        val drawable = GradientDrawable()

        drawable.shape = if (oval) {
            GradientDrawable.OVAL
        } else {
            GradientDrawable.RECTANGLE
        }

        drawable.setColor(color)
        drawable.cornerRadius = 8f

        return drawable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
