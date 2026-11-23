package com.example.wildanfahrezy_3tib

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wildanfahrezy_3tib.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        sharedPref = getSharedPreferences("Mindsheet", MODE_PRIVATE)

        binding.btnRegister.setOnClickListener { doRegister() }
        binding.tvToLogin.setOnClickListener { finish() }
    }

    private fun doRegister() {
        val name = binding.edtName.text.toString().trim()
        val user = binding.edtUsername.text.toString().trim()
        val pass = binding.edtPassword.text.toString()
        val confirm = binding.edtConfirm.text.toString()

        binding.tilName.error = null
        binding.tilUsername.error = null
        binding.tilPassword.error = null
        binding.tilConfirm.error = null

        if (name.isEmpty()) {
            binding.tilName.error = "Nama panggilan wajib diisi"
            return
        }
        if (user.length < 4) {
            binding.tilUsername.error = "Username minimal 4 karakter"
            return
        }
        if (pass.length < 6) {
            binding.tilPassword.error = "Password minimal 6 karakter"
            return
        }
        if (confirm != pass) {
            binding.tilConfirm.error = "Konfirmasi password tidak sama"
            return
        }

        val editor = sharedPref.edit()
        editor.putString("name", name)
        editor.putString("username", user)
        editor.putString("password", pass)
        editor.apply()

        Toast.makeText(this, "Akun berhasil dibuat. Silakan masuk.", Toast.LENGTH_SHORT).show()
        finish()
    }
}
