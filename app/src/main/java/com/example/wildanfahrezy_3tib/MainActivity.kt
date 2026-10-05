package com.example.wildanfahrezy_3tib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wildanfahrezy_3tib.databinding.ActivityMainBinding
import com.example.wildanfahrezy_3tib.pertemuan5.LimaActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usr = intent.getStringExtra("username")
        val pwd = intent.getStringExtra("password")
        val umur = intent.getIntExtra("umur", 0)

        Log.e("Hasil", "umur $umur Username $usr Password $pwd")


        binding.txtUsername.text = usr
        binding.txtPassword.setText(pwd)

        binding.btnDetail.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        binding.btnSnackBar.setOnClickListener {
            Snackbar.make(binding.root, "aplikasi untuk menulis jurnal harian",
                Snackbar.LENGTH_LONG)
                .setAction("oke")

                {
                    //val intent = Intent(this, Login::class.java)
                    //startActivity(intent)


                }
                .show()
        }

        binding.btnAlertDialog.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Ya") { _, _ ->
                    val intent = Intent(this, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setCancelable(false)
                .show()
        }


        binding.btnKembali.setOnClickListener {
//            val intent = Intent(this, Login::class.java)
//            startActivity(intent)

            finish()

        }

        binding.btnToLima.setOnClickListener{
            startActivity(Intent(this, LimaActivity::class.java))
        }
    }
}