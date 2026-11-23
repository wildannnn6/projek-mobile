package com.example.wildanfahrezy_3tib

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wildanfahrezy_3tib.databinding.ActivityAffirmationDetailBinding

class AffirmationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAffirmationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAffirmationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Afirmasi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        val category = intent.getStringExtra(EXTRA_CATEGORY) ?: ""
        val message = intent.getStringExtra(EXTRA_MESSAGE) ?: ""
        val iconRes = intent.getIntExtra(EXTRA_ICON, R.drawable.aff_heart)

        binding.txtCategory.text = category
        binding.txtMessage.text = "\"$message\""
        binding.imgIcon.setImageResource(iconRes)

        // Bagikan lewat aplikasi lain (WhatsApp, pesan, dll.)
        binding.btnShare.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_TEXT, "\"$message\"\n- $category (MindSheet)")
            startActivity(Intent.createChooser(share, "Bagikan afirmasi"))
        }

        // Buka halaman tulis jurnal dengan isi awal dari afirmasi ini
        binding.btnWriteJournal.setOnClickListener {
            val write = Intent(this, WriteJournalActivity::class.java)
            write.putExtra(WriteJournalActivity.EXTRA_PREFILL, message)
            startActivity(write)
        }
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
        const val EXTRA_CATEGORY = "CATEGORY"
        const val EXTRA_MESSAGE = "MESSAGE"
        const val EXTRA_ICON = "ICON"

        // Dipakai halaman daftar afirmasi dan kartu di Beranda supaya Intent-nya seragam
        fun newIntent(context: Context, data: AffirmationModel): Intent {
            val intent = Intent(context, AffirmationDetailActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY, data.category)
            intent.putExtra(EXTRA_MESSAGE, data.message)
            intent.putExtra(EXTRA_ICON, data.iconRes)
            return intent
        }
    }
}
