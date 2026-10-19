package com.example.wildanfahrezy_3tib.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.wildanfahrezy_3tib.DetailActivity
import com.example.wildanfahrezy_3tib.Home.pertemuan5.LimaActivity
import com.example.wildanfahrezy_3tib.Home.pertemuan6.SixthActivity
import com.example.wildanfahrezy_3tib.Login
import com.example.wildanfahrezy_3tib.R
import com.example.wildanfahrezy_3tib.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for requireContext() fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)

        val usr = requireActivity().intent.getStringExtra("username")
        val pwd = requireActivity().intent.getStringExtra("password")
        val umur = requireActivity().intent.getIntExtra("umur", 0)

        Log.e("Hasil", "umur $umur Username $usr Password $pwd")


        binding.txtUsername.text = usr
        binding.txtPassword.setText(pwd)

        binding.btnDetail.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            startActivity(intent)
        }

        binding.btnSnackBar.setOnClickListener {
            Snackbar.make(binding.root, "aplikasi untuk menulis jurnal harian",
                Snackbar.LENGTH_LONG)
                .setAction("oke")

                {
                    //val intent = Intent(requireContext(), Login::class.java)
                    //startActivity(intent)


                }
                .show()
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()
                    dialog.dismiss()
                    startActivity(Intent(requireContext(), Login::class.java))

                    requireActivity().finish()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }


        binding.btnKembali.setOnClickListener {
//            val intent = Intent(requireContext(), Login::class.java)
//            startActivity(intent)

            requireActivity().finish()

        }

        binding.btnToLima.setOnClickListener{
            startActivity(Intent(requireContext(), LimaActivity::class.java))
        }

        binding.btnToSix.setOnClickListener{
            startActivity(Intent(requireContext(), SixthActivity::class.java))
        }

    }
}
