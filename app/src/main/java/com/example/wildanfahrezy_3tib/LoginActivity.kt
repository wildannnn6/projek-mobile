package com.example.wildanfahrezy_3tib

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wildanfahrezy_3tib.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ime = keyboard, supaya tombol tidak tertutup saat mengetik
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        sharedPref = getSharedPreferences("Mindsheet", MODE_PRIVATE)

        binding.btnLogin.setOnClickListener { doLogin() }
        binding.tvRegister.setOnClickListener { openRegister() }
        binding.tvForgot.setOnClickListener { confirmReset() }
    }

    private fun doLogin() {
        val user = binding.edtUsername.text.toString().trim()
        val pass = binding.edtPassword.text.toString()
        val savedUser = sharedPref.getString("username", null)
        val savedPass = sharedPref.getString("password", null)

        binding.tilUsername.error = null
        binding.tilPassword.error = null

        // belum ada akun yang didaftarkan di perangkat ini
        if (savedUser == null || savedPass == null) {
            AlertDialog.Builder(this)
                .setTitle("Belum Ada Akun")
                .setMessage("Buat akun dulu untuk mulai menulis jurnal.")
                .setPositiveButton("Daftar") { _, _ ->
                    startActivity(Intent(this, RegisterActivity::class.java))
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
            return
        }

        if (user.isEmpty()) {
            binding.tilUsername.error = "Username wajib diisi"
            return
        }
        if (pass.isEmpty()) {
            binding.tilPassword.error = "Password wajib diisi"
            return
        }

        if (user == savedUser && pass == savedPass) {
            sharedPref.edit().putBoolean("isLogin", true).apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Login Gagal")
                .setMessage("Username atau password salah. Silakan coba lagi.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    private fun openRegister() {
        if (sharedPref.getString("username", null) != null) {
            Toast.makeText(
                this,
                "Akun sudah terdaftar di perangkat ini. Pakai \"Lupa password?\" untuk mendaftar ulang.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // Tidak ada server/email, jadi "lupa password" = hapus akun lokal lalu daftar ulang.
    // Jurnal di database tidak ikut terhapus.
    private fun confirmReset() {
        AlertDialog.Builder(this)
            .setTitle("Reset Akun")
            .setMessage("Akun di perangkat ini akan dihapus agar kamu bisa mendaftar ulang. Jurnal yang sudah kamu tulis tidak ikut terhapus.")
            .setPositiveButton("Reset") { _, _ ->
                sharedPref.edit()
                    .remove("username")
                    .remove("password")
                    .remove("name")
                    .apply()
                Toast.makeText(this, "Akun direset. Silakan daftar ulang.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
