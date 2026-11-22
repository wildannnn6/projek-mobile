package com.example.wildanfahrezy_3tib

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.wildanfahrezy_3tib.R
import com.example.wildanfahrezy_3tib.databinding.ActivityWriteJournalBinding
import kotlinx.coroutines.launch

class WriteJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteJournalBinding
    private lateinit var db: AppDatabase

    // Jurnal yang sedang dibuka (diisi setelah dibaca dari database)
    private var journal: NoteEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWriteJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = AppDatabase.getInstance(this)

        // id jurnal dikirim dari halaman utama lewat Intent
        val journalId = intent.getIntExtra(EXTRA_JOURNAL_ID, -1)
        if (journalId == -1) {
            finish()
            return
        }

        loadJournal(journalId)

        binding.btnSave.setOnClickListener {
            saveJournal()
        }
        binding.btnDelete.setOnClickListener {
            confirmDelete()
        }
    }

    // READ: ambil jurnal dari database lalu isi ke kolom judul dan isi
    private fun loadJournal(id: Int) {
        lifecycleScope.launch {
            val result = db.noteDao().getById(id)
            if (result == null) {
                finish()
                return@launch
            }
            journal = result
            binding.etTitle.setText(result.title)
            binding.etContent.setText(result.content)
        }
    }

    // UPDATE: simpan perubahan ke database
    private fun saveJournal() {
        val old = journal ?: return
        val title = binding.etTitle.text.toString().trim()
        val content = binding.etContent.text.toString().trim()
        if (content.isBlank()) {
            Toast.makeText(this, "Isi jurnal tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        // id dan createdAt tetap sama, hanya judul dan isi yang berubah
        val updated = NoteEntity(
            id = old.id,
            title = title,
            content = content,
            createdAt = old.createdAt
        )

        binding.btnSave.isEnabled = false
        lifecycleScope.launch {
            try {
                db.noteDao().update(updated)
                Toast.makeText(this@WriteJournalActivity, "Jurnal disimpan", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                binding.btnSave.isEnabled = true
                Toast.makeText(
                    this@WriteJournalActivity,
                    "Gagal menyimpan jurnal",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // DELETE: tanya dulu, kalau "Ya" hapus dari database
    private fun confirmDelete() {
        val old = journal ?: return
        AlertDialog.Builder(this)
            .setTitle("Hapus Jurnal")
            .setMessage("Yakin ingin menghapus jurnal ini?")
            .setPositiveButton("Ya") { _, _ ->
                lifecycleScope.launch {
                    db.noteDao().delete(old)
                    finish()
                }
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    companion object {
        const val EXTRA_JOURNAL_ID = "JOURNAL_ID"
    }
}
