package com.example.mutia.Profil

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mutia.DetailActivity
import com.example.mutia.LoginActivity
import com.example.mutia.R
import com.example.mutia.databinding.FragmentProfilBinding

data class MenuModel(
    val title: String,
    val iconRes: Int,
    val trailing: String? = null,
    val trailingBadge: Boolean = false,
    val danger: Boolean = false
)

private fun bentuk(context: Context, color: Int, radiusDp: Float = 0f, oval: Boolean = false): GradientDrawable {
    val d = GradientDrawable()
    d.shape = if (oval) GradientDrawable.OVAL else GradientDrawable.RECTANGLE
    d.setColor(color)
    d.cornerRadius = radiusDp * context.resources.displayMetrics.density
    return d
}

private class MenuAdapter(
    context: Context,
    private val items: List<MenuModel>
) : ArrayAdapter<MenuModel>(context, 0, items) {

    private fun dp(v: Int) = (v * context.resources.displayMetrics.density).toInt()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = items[position]

        val row = LinearLayout(context)
        row.orientation = LinearLayout.HORIZONTAL
        row.gravity = Gravity.CENTER_VERTICAL
        row.setPadding(dp(16), 0, dp(16), 0)
        row.layoutParams = AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(TINGGI_BARIS))

        val warnaIkon = if (item.danger) "#D93A3A" else "#8A4B5E"
        val warnaKotak = if (item.danger) "#FDE8E8" else "#F3EEF8"
        val icon = ImageView(context)
        icon.layoutParams = LinearLayout.LayoutParams(dp(44), dp(44))
        icon.setPadding(dp(11), dp(11), dp(11), dp(11))
        icon.setImageResource(item.iconRes)
        icon.imageTintList = ColorStateList.valueOf(Color.parseColor(warnaIkon))
        icon.background = bentuk(context, Color.parseColor(warnaKotak), 14f)
        row.addView(icon)

        val title = TextView(context)
        title.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
            marginStart = dp(14)
            marginEnd = dp(8)
        }
        title.text = item.title
        title.textSize = 15f
        title.setTextColor(Color.parseColor(if (item.danger) "#D93A3A" else "#33252D"))
        title.maxLines = 2
        row.addView(title)

        if (item.trailing != null) {
            val trailing = TextView(context)
            trailing.text = item.trailing
            if (item.trailingBadge) {
                trailing.textSize = 11f
                trailing.setTypeface(null, Typeface.BOLD)
                trailing.setTextColor(Color.parseColor("#1B8A3E"))
                trailing.setPadding(dp(10), dp(3), dp(10), dp(3))
                trailing.background = bentuk(context, Color.parseColor("#DFF5E5"), 10f)
            } else {
                trailing.textSize = 12f
                trailing.setTextColor(Color.parseColor("#827780"))
            }
            row.addView(trailing)
        }

        val panah = TextView(context)
        panah.text = "›"
        panah.textSize = 24f
        panah.setTextColor(Color.parseColor(if (item.danger) "#D93A3A" else "#B9A5B0"))
        panah.setPadding(dp(8), 0, 0, 0)
        row.addView(panah)

        return row
    }

    companion object {
        const val TINGGI_BARIS = 68
    }
}

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val menuList = listOf(
        MenuModel("Notifikasi & Pengingat", android.R.drawable.ic_lock_idle_alarm, "Aktif", trailingBadge = true),
        MenuModel("Keamanan Akun & PIN", android.R.drawable.ic_secure),
        MenuModel("Pengaturan Aplikasi & Bahasa", android.R.drawable.ic_menu_preferences, "Bahasa Indonesia"),
        MenuModel("Pusat Bantuan & Tanya Bidan", android.R.drawable.ic_menu_help),
        MenuModel("Detail Aplikasi", android.R.drawable.ic_menu_info_details),
        MenuModel("Keluar dari Akun", android.R.drawable.ic_lock_power_off, danger = true)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ctx = requireContext()

        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.apply {
            title = " Profil"
            subtitle = " MomCare"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.momcare_logo)
            setDisplayUseLogoEnabled(true)
        }

        val urlFoto = "https://avatar.iran.liara.run/public/8"
        Glide.with(ctx).load(urlFoto).circleCrop().into(binding.imgAvatar)
        Glide.with(ctx).load(urlFoto).circleCrop().into(binding.imgAvatarToolbar)

        binding.tvBadgeVerifikasi.background = bentuk(ctx, Color.parseColor("#FBE4EC"), 8f)
        binding.tvCentang.background = bentuk(ctx, Color.parseColor("#2E9E75"), oval = true)
        binding.btnEditProfil.background = bentuk(ctx, Color.parseColor("#F8E6EE"), oval = true)

        binding.imgBell.setOnClickListener {
            Toast.makeText(ctx, "Notifikasi", Toast.LENGTH_SHORT).show()
        }
        binding.btnEditProfil.setOnClickListener {
            Toast.makeText(ctx, "Edit profil", Toast.LENGTH_SHORT).show()
        }

        binding.listViewMenu.adapter = MenuAdapter(ctx, menuList)

        val lv = binding.listViewMenu
        val tinggi = menuList.size * dp(MenuAdapter.TINGGI_BARIS) + lv.dividerHeight * (menuList.size - 1)
        val params = lv.layoutParams
        params.height = tinggi
        lv.layoutParams = params

        lv.setOnItemClickListener { _, _, position, _ ->
            val item = menuList[position]
            when {
                item.danger -> tampilkanDialogLogout()
                item.title == "Detail Aplikasi" -> bukaDetailAplikasi()
                else -> Toast.makeText(ctx, item.title, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun bukaDetailAplikasi() {
        val intent = Intent(
            requireContext(),
            DetailActivity::class.java
        )
        startActivity(intent)
    }


    private fun tampilkanDialogLogout() {
        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { dialog, _ ->
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()
                dialog.dismiss()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
