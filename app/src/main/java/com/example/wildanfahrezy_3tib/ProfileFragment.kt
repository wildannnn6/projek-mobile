package com.example.wildanfahrezy_3tib

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.wildanfahrezy_3tib.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase

    // Isi menu: ikon, judul, dan keterangan singkat
    private val menuList: List<Map<String, Any>> = listOf(
        mapOf("icon" to R.drawable.aff_leaf, "title" to "Panduan Self-Care", "desc" to "Latihan napas, grounding, dan tidur nyenyak"),
        mapOf("icon" to R.drawable.aff_book, "title" to "Edukasi Kesehatan Mental", "desc" to "Pelajari dasar-dasar kesehatan mental"),
        mapOf("icon" to R.drawable.ic_call, "title" to "Bantuan Darurat", "desc" to "Layanan konseling gratis Healing119"),
        mapOf("icon" to R.drawable.ic_info, "title" to "Tentang Aplikasi", "desc" to "Informasi MindSheet"),
        mapOf("icon" to R.drawable.ic_delete, "title" to "Hapus Semua Jurnal", "desc" to "Kosongkan seluruh catatan jurnal"),
        mapOf("icon" to R.drawable.ic_logout, "title" to "Keluar", "desc" to "Keluar dari akun di perangkat ini")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())

        val sharedPref = requireContext().getSharedPreferences("Mindsheet", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "-")
        val name = sharedPref.getString("name", null) ?: username
        binding.tvName.text = name
        binding.tvUsername.text = "@$username"

        // SimpleAdapter: menghubungkan menuList dengan layout item_profile_menu
        val adapter = SimpleAdapter(
            requireContext(),
            menuList,
            R.layout.item_profile_menu,
            arrayOf("icon", "title", "desc"), // key yang diambil dari data
            intArrayOf(R.id.imgMenuIcon, R.id.tvMenuTitle, R.id.tvMenuDesc) // tempat tampilnya
        )
        binding.listMenu.adapter = adapter
        binding.listMenu.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> startActivity(Intent(requireContext(), SelfCareGuideActivity::class.java))
                1 -> startActivity(Intent(requireContext(), EducationActivity::class.java))
                2 -> startActivity(Intent(requireContext(), HelpActivity::class.java))
                3 -> showAbout()
                4 -> confirmDeleteAll()
                5 -> confirmLogout()
                else -> {}
            }
        }
    }

    // Jumlah jurnal di-update setiap halaman ini tampil
    override fun onResume() {
        super.onResume()
        loadTotal()
    }

    private fun loadTotal() {
        viewLifecycleOwner.lifecycleScope.launch {
            val total = db.journalDao().count()
            binding.tvTotal.text = "$total jurnal tertulis"
        }
    }

    private fun showAbout() {
        AlertDialog.Builder(requireContext())
            .setTitle("Tentang MindSheet")
            .setMessage(
                "MindSheet adalah aplikasi jurnal untuk menuliskan perasaan, " +
                        "memantau mood, dan menemukan panduan self-care.\n\n" +
                        "Versi 1.0\nDibuat oleh Wildan Fahrezy (3TIB)\n\n" +
                        "Aplikasi ini bukan pengganti tenaga kesehatan profesional."
            )
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun confirmDeleteAll() {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Semua Jurnal")
            .setMessage("Semua jurnal akan dihapus dan tidak bisa dikembalikan. Lanjutkan?")
            .setPositiveButton("Hapus") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    db.journalDao().deleteAll()
                    Toast.makeText(requireContext(), "Semua jurnal dihapus", Toast.LENGTH_SHORT).show()
                    loadTotal()
                }
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun confirmLogout() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { dialog, _ ->
                // hanya status login yang diubah, akun tetap tersimpan
                val sharedPref = requireContext().getSharedPreferences("Mindsheet", Context.MODE_PRIVATE)
                sharedPref.edit().putBoolean("isLogin", false).apply()
                dialog.dismiss()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
