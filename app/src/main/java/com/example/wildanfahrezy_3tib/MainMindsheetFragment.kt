package com.example.wildanfahrezy_3tib

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wildanfahrezy_3tib.databinding.FragmentMainMindsheetBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainMindsheetFragment : Fragment() {

    private var _binding: FragmentMainMindsheetBinding? = null

    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: JournalAdapter
    private val journals = mutableListOf<NoteEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainMindsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val sharedPref = requireContext().getSharedPreferences("Mindsheet", MODE_PRIVATE)

        db = AppDatabase.getInstance(requireContext())

        // RecyclerView daftar kertas jurnal
        adapter = JournalAdapter(
            journals,
            onClick = { journal -> openJournal(journal) },
            onDelete = { journal -> deleteJournal(journal) }
        )
        binding.rvJournals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJournals.adapter = adapter

        // Tombol tambah (kanan atas): buat kertas jurnal baru yang masih kosong
        binding.btnAdd.setOnClickListener {
            addBlankJournal()
        }

        binding.btnSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        binding.btnSnackBar.setOnClickListener {
            Snackbar.make(binding.root, "aplikasi untuk menulis jurnal harian",
                Snackbar.LENGTH_LONG)
                .setAction("oke")
                {
                    //val intent = Intent(requireContext(), LoginActivity::class.java)
                    //startActivity(intent)
                }
                .show()
        }

        binding.btnToArticle.setOnClickListener {
            startActivity(Intent(requireActivity(), ArticleActivity::class.java))
        }

        binding.btnToSelfCare.setOnClickListener{
            startActivity(Intent(requireContext(), SelfCareGuideActivity::class.java))
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
            val result = db.noteDao().getAll()
            journals.clear()
            journals.addAll(result)
            adapter.notifyDataSetChanged()

            // Belum ada jurnal -> tampilkan tulisan di tengah halaman
            if (journals.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.tvEmpty.visibility = View.GONE
            }
        }
    }

    private fun addBlankJournal() {
        viewLifecycleOwner.lifecycleScope.launch {
            db.noteDao().insert(NoteEntity(title = "", content = ""))
            fetchJournals()
        }
    }

    private fun openJournal(journal: NoteEntity) {
        val intent = Intent(requireContext(), WriteJournalActivity::class.java)
        intent.putExtra(WriteJournalActivity.EXTRA_JOURNAL_ID, journal.id)
        startActivity(intent)
    }

    private fun deleteJournal(journal: NoteEntity) {
        viewLifecycleOwner.lifecycleScope.launch {
            db.noteDao().delete(journal)
            fetchJournals()
        }
    }
}
