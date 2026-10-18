package com.example.wildanfahrezy_3tib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wildanfahrezy_3tib.databinding.ActivityMainBinding
import com.example.wildanfahrezy_3tib.databinding.ActivityMainMindhseetBinding

class mainMindhseet : AppCompatActivity() {

    private lateinit var binding: ActivityMainMindhseetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainMindhseetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("Mindsheet", MODE_PRIVATE)

        binding.cardButton.setOnClickListener {
            val intent = Intent(this, halaman_tulis::class.java)
            startActivity(intent)
        }

        binding.btnSetting.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

    }
}