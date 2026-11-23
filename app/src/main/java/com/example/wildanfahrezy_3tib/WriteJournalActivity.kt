package com.example.wildanfahrezy_3tib

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.wildanfahrezy_3tib.databinding.ActivityWriteJournalBinding
import com.example.wildanfahrezy_3tib.databinding.ItemMoodChoiceBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Satu halaman untuk DUA mode:
//  - jurnal baru  : dibuka tanpa EXTRA_JOURNAL_ID
//  - edit jurnal  : dibuka dengan EXTRA_JOURNAL_ID
class WriteJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteJournalBinding
    private lateinit var db: AppDatabase

    // id jurnal yang diedit (-1 = jurnal baru)
    private var journalId = -1

    // Jurnal yang sedang dibuka (diisi setelah dibaca dari database)
    private var journal: JournalEntity? = null

    private var selectedMood = MoodModel.DEFAULT_INDEX
    private val moodItems = mutableListOf<ItemMoodChoiceBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWriteJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ime = keyboard, supaya tombol Simpan tetap terlihat saat mengetik
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        db = AppDatabase.getInstance(this)

        // mood awal bisa dikirim dari Beranda (ketuk wajah mood)
        selectedMood = intent.getIntExtra(EXTRA_MOOD, MoodModel.DEFAULT_INDEX)
        buildMoodPicker()

        // id jurnal dikirim dari halaman daftar lewat Intent
        journalId = intent.getIntExtra(EXTRA_JOURNAL_ID, -1)
        if (journalId == -1) {
            prepareNewJournal()
        } else {
            supportActionBar?.title = "Edit Jurnal"
            loadJournal(journalId)
        }

        binding.btnSave.setOnClickListener {
            saveJournal()
        }
        binding.btnDelete.setOnClickListener {
            confirmDelete()
        }
    }

    // Membuat lima pilihan mood dari MoodModel.all
    private fun buildMoodPicker() {
        for (index in MoodModel.all.indices) {
            val mood = MoodModel.all[index]
            val item = ItemMoodChoiceBinding.inflate(layoutInflater, binding.llMoodPicker, false)
            item.ivMood.setImageResource(mood.iconRes)
            item.tvMood.text = mood.name
            item.root.setOnClickListener { selectMood(index) }
            binding.llMoodPicker.addView(item.root)
            moodItems.add(item)
        }
        selectMood(selectedMood)
    }

    // Mood terpilih diberi bingkai, yang lain dibuat agak pudar
    private fun selectMood(index: Int) {
        selectedMood = index
        for (i in moodItems.indices) {
            if (i == index) {
                moodItems[i].root.setBackgroundResource(R.drawable.bg_mood_selected)
                moodItems[i].root.alpha = 1f
            } else {
                moodItems[i].root.setBackgroundResource(0)
                moodItems[i].root.alpha = 0.55f
            }
        }
    }

    private fun prepareNewJournal() {
        supportActionBar?.title = "Jurnal Baru"
        binding.btnDelete.visibility = View.GONE
        binding.tvDate.text = formatDate(System.currentTimeMillis())

        // isi awal bisa dikirim dari halaman Afirmasi ("Tulis di jurnal")
        val prefill = intent.getStringExtra(EXTRA_PREFILL)
        if (prefill != null) {
            binding.etContent.setText(prefill)
        }
    }

    // READ: ambil jurnal dari database lalu isi ke kolom judul dan isi
    private fun loadJournal(id: Int) {
        lifecycleScope.launch {
            val result = db.journalDao().getById(id)
            if (result == null) {
                finish()
                return@launch
            }
            journal = result
            binding.tvDate.text = formatDate(result.createdAt)
            binding.etTitle.setText(result.title)
            binding.etContent.setText(result.content)
            selectMood(result.mood.coerceIn(0, MoodModel.all.size - 1))
        }
    }

    // CREATE (jurnal baru) atau UPDATE (jurnal lama) saat tombol Simpan ditekan
    private fun saveJournal() {
        val old = journal
        // mode edit tapi data belum selesai dimuat -> tunggu dulu
        if (journalId != -1 && old == null) return

        val title = binding.etTitle.text.toString().trim()
        val content = binding.etContent.text.toString().trim()
        if (content.isBlank()) {
            binding.tilContent.error = "Isi jurnal tidak boleh kosong"
            return
        }
        binding.tilContent.error = null

        binding.btnSave.isEnabled = false
        lifecycleScope.launch {
            try {
                if (old == null) {
                    db.journalDao().insert(
                        JournalEntity(title = title, content = content, mood = selectedMood)
                    )
                } else {
                    // id dan createdAt tetap sama, hanya judul, isi, dan mood yang berubah
                    db.journalDao().update(
                        JournalEntity(
                            id = old.id,
                            title = title,
                            content = content,
                            mood = selectedMood,
                            createdAt = old.createdAt
                        )
                    )
                }
                Toast.makeText(this@WriteJournalActivity, "Jurnal disimpan", Toast.LENGTH_SHORT).show()
                afterSave()
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

    // Mood Cemas/Sedih (urutan 3 dan 4): tawarkan panduan self-care sebelum menutup halaman
    private fun afterSave() {
        if (selectedMood >= 3) {
            AlertDialog.Builder(this)
                .setTitle("Terima kasih sudah menulis")
                .setMessage("Menuliskan perasaan adalah langkah yang baik. Kalau terasa berat, coba latihan singkat di Panduan Self-Care.")
                .setPositiveButton("Buka Self-Care") { _, _ ->
                    startActivity(Intent(this, SelfCareGuideActivity::class.java))
                    finish()
                }
                .setNegativeButton("Nanti saja") { _, _ -> finish() }
                .setCancelable(false)
                .show()
        } else {
            finish()
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
                    db.journalDao().delete(old)
                    finish()
                }
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun formatDate(millis: Long): String {
        val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy - HH:mm", Locale.getDefault())
        return formatter.format(Date(millis))
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

    companion object {
        const val EXTRA_JOURNAL_ID = "JOURNAL_ID"
        const val EXTRA_MOOD = "MOOD"
        const val EXTRA_PREFILL = "PREFILL"
    }
}
