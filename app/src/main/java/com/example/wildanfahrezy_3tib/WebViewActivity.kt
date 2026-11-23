package com.example.wildanfahrezy_3tib

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wildanfahrezy_3tib.databinding.ActivityWebViewBinding

// Dipakai ulang untuk beberapa halaman web. Alamat dan judul dikirim lewat Intent;
// kalau tidak dikirim, otomatis membuka artikel kesehatan mental Halodoc.
class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val url = intent.getStringExtra(EXTRA_URL) ?: "https://www.halodoc.com/kesehatan-mental"
        val pageTitle = intent.getStringExtra(EXTRA_TITLE) ?: "Halodoc - Kesehatan Mental"

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = pageTitle
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)
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
        const val EXTRA_URL = "URL"
        const val EXTRA_TITLE = "TITLE"
    }
}
