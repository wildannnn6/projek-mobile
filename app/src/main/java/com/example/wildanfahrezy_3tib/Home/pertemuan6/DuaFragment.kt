package com.example.wildanfahrezy_3tib.Home.pertemuan6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wildanfahrezy_3tib.R
import com.example.wildanfahrezy_3tib.databinding.FragmentDuaBinding


class DuaFragment : Fragment() {

    private var _binding: FragmentDuaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtJudul.text = "🌿 Pertolongan Pertama Stres Kerja / Belajar"
        binding.txtDeskripsi.text = """
            Langkah cepat meredakan kelelahan mental (burnout):

            1. Istirahat Layar (Rule 20-20-20)
               Alihkan pandangan dari laptop/HP setiap 20 menit ke objek sejauh 6 meter selama 20 detik.

            2. Peregangan Singkat
               Bongkokkan badan, putar bahu ke belakang, dan rilekskan otot leher yang tegang.

            3. Batasi Prioritas
               Pilih 1 tugas paling penting hari ini. Biarkan sisanya dikerjakan secara bertahap.

            ☕ Minum segelas air putih hangat untuk membantu melancarkan sirkulasi darah.
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}