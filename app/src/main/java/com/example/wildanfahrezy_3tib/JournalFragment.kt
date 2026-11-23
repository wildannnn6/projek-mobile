package com.example.wildanfahrezy_3tib

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wildanfahrezy_3tib.databinding.FragmentJournalBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: JournalAdapter
    private val journals = mutableListOf<JournalEntity>()

    // kata kunci pencarian yang sedang dipakai (kosong = tampilkan semua)
    private var keyword = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())

        // RecyclerView daftar jurnal
        adapter = JournalAdapter(
            journals,
            onClick = { journal -> openJournal(journal) },
            onDelete = { journal -> deleteJournal(journal) }
        )
        binding.rvJournals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJournals.adapter = adapter

        // Tombol tambah: buka halaman tulis dalam mode jurnal baru
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), WriteJournalActivity::class.java))
        }

        // Tombol cari: ambil teks di kolom pencarian lalu muat ulang daftar
        binding.btnSearch.setOnClickListener {
            keyword = binding.edtSearch.text.toString().trim()
            fetchJournals()
        }
    }

    // Dipanggil setiap halaman ini tampil lagi (misal setelah balik dari halaman tulis),
    // jadi daftar selalu mengikuti isi database terbaru.
    override fun onResume() {
        super.onResume()
        fetchJournals()
    }

    private fun fetchJournals() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = if (keyword.isEmpty()) {
                db.journalDao().getAll()
            } else {
                db.journalDao().search(keyword)
            }
            journals.clear()
            journals.addAll(result)
            adapter.notifyDataSetChanged()

            binding.tvCount.text = "${journals.size} jurnal"

            // Daftar kosong -> tampilkan tulisan di tengah halaman
            if (journals.isEmpty()) {
                binding.llEmpty.visibility = View.VISIBLE
                if (keyword.isEmpty()) {
                    binding.tvEmpty.text = "Belum ada jurnal.\nKetuk tombol + untuk mulai menulis."
                } else {
                    binding.tvEmpty.text = "Tidak ada jurnal yang cocok dengan \"$keyword\"."
                }
            } else {
                binding.llEmpty.visibility = View.GONE
            }
        }
    }

    private fun openJournal(journal: JournalEntity) {
        val intent = Intent(requireContext(), WriteJournalActivity::class.java)
        intent.putExtra(WriteJournalActivity.EXTRA_JOURNAL_ID, journal.id)
        startActivity(intent)
    }

    // Hapus lalu tampilkan Snackbar dengan tombol "Urungkan" (data dimasukkan lagi)
    private fun deleteJournal(journal: JournalEntity) {
        viewLifecycleOwner.lifecycleScope.launch {
            db.journalDao().delete(journal)
            fetchJournals()

            Snackbar.make(binding.root, "Jurnal dihapus", Snackbar.LENGTH_LONG)
                .setAction("Urungkan") {
                    viewLifecycleOwner.lifecycleScope.launch {
                        db.journalDao().insert(journal)
                        fetchJournals()
                    }
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
