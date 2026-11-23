package com.example.wildanfahrezy_3tib

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.wildanfahrezy_3tib.databinding.ActivitySelfCareGuideBinding

class SelfCareGuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfCareGuideBinding
    private lateinit var tabs: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySelfCareGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Panduan Self-Care"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        tabs = listOf(
            binding.btnBreathing,
            binding.btnStressAid,
            binding.btnGrounding,
            binding.btnSleep
        )

        // Menampilkan fragment pertama secara default
        showTab(BreathingTechniqueFragment(), binding.btnBreathing)

        // Setup event click untuk mengganti fragment
        binding.btnBreathing.setOnClickListener { showTab(BreathingTechniqueFragment(), binding.btnBreathing) }
        binding.btnStressAid.setOnClickListener { showTab(StressFirstAidFragment(), binding.btnStressAid) }
        binding.btnGrounding.setOnClickListener { showTab(GroundingFragment(), binding.btnGrounding) }
        binding.btnSleep.setOnClickListener { showTab(SleepHygieneFragment(), binding.btnSleep) }

        // Setelah latihan, langsung catat perasaan di jurnal baru
        binding.btnWriteFeeling.setOnClickListener {
            startActivity(Intent(this, WriteJournalActivity::class.java))
        }
    }

    // Ganti fragment, lalu tombol yang aktif dibuat jelas dan yang lain agak pudar
    private fun showTab(fragment: Fragment, selected: Button) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
        for (tab in tabs) {
            if (tab == selected) {
                tab.alpha = 1f
            } else {
                tab.alpha = 0.55f
            }
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
}
