package com.example.wildanfahrezy_3tib

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.wildanfahrezy_3tib.R
import com.example.wildanfahrezy_3tib.databinding.ActivitySelfCareGuideBinding

class SelfCareGuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfCareGuideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding= ActivitySelfCareGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Panduan Self-Care"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ice_arrow_back)
        }
// Menampilkan fragment pertama secara default
        replaceFragment(BreathingTechniqueFragment(), addToBackStack = false)
// Setup event click untuk mengganti fragment
        binding.btnBreathing.setOnClickListener { replaceFragment(BreathingTechniqueFragment()) }
        binding.btnStressAid.setOnClickListener { replaceFragment(StressFirstAidFragment()) }
        binding.btnSleep.setOnClickListener { replaceFragment(SleepHygieneFragment()) }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
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

