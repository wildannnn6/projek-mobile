package com.example.wildanfahrezy_3tib.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wildanfahrezy_3tib.DetailActivity
import com.example.wildanfahrezy_3tib.Home.pertemuan5.LimaActivity
import com.example.wildanfahrezy_3tib.Home.pertemuan5.WebViewActivity
import com.example.wildanfahrezy_3tib.Home.pertemuan6.SixthActivity
import com.example.wildanfahrezy_3tib.R
import com.example.wildanfahrezy_3tib.Setting
import com.example.wildanfahrezy_3tib.databinding.ActivityMainMindhseetBinding
import com.example.wildanfahrezy_3tib.databinding.FragmentHomeBinding
import com.example.wildanfahrezy_3tib.databinding.FragmentMainMindsheetBinding
import com.example.wildanfahrezy_3tib.halaman_tulis
import com.google.android.material.snackbar.Snackbar

class MainMindsheetFragment : Fragment() {

    private var _binding: FragmentMainMindsheetBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainMindsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val sharedPref = requireContext().getSharedPreferences("Mindsheet", MODE_PRIVATE)

        binding.cardButton.setOnClickListener {
            val intent = Intent(requireContext(), halaman_tulis::class.java)
            startActivity(intent)
        }

        binding.btnSetting.setOnClickListener {
            val intent = Intent(requireContext(), Setting::class.java)
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

        binding.btnToLima.setOnClickListener {
            startActivity(Intent(requireActivity(), LimaActivity::class.java))
        }

        binding.btnToSix.setOnClickListener{
            startActivity(Intent(requireContext(), SixthActivity::class.java))
        }

    }
}