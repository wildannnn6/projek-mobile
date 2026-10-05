package com.example.wildanfahrezy_3tib

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wildanfahrezy_3tib.databinding.ActivityLoginBinding
import kotlin.math.log

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //val tombolLogin : Button = findViewById(R.id.BtnLogin)
        //val usr : EditText = findViewById(R.id.edtusr)
        //val pwd : EditText = findViewById(R.id.edtpwd)

        //tombolLogin.setOnClickListener {

        binding.BtnLogin.setOnClickListener {
            val usr = binding.edtusr.text.toString()
            val pwd = binding.edtpwd.text.toString()

            Log.e("Hasil", "Username $usr Password $pwd")

            Toast.makeText(this, "Username $usr Password $pwd", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username", usr)
            intent.putExtra("password", pwd)
            intent.putExtra("umur", 1945)


            startActivity(intent)
        }
    }
}