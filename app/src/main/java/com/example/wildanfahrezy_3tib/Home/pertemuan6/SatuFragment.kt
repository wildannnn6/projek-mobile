package com.example.wildanfahrezy_3tib.Home.pertemuan6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wildanfahrezy_3tib.R
import com.example.wildanfahrezy_3tib.databinding.FragmentSatuBinding


class SatuFragment : Fragment() {

    private var _binding: FragmentSatuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSatuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengisi teks materi relaksasi cemas
        binding.txtJudul.text = "🫁 Teknik Pernapasan 4-7-8"
        binding.txtDeskripsi.text = """
            Gunakan teknik ini saat merasa panik, cemas, atau pikiran terasa penuh:

            1. Tarik Napas (4 Detik)
               Tarik napas perlahan melalui hidung dalam hitungan 4 detik.

            2. Tahan Napas (7 Detik)
               Tahan napas kamu dan fokuskan pikiran pada ketenangan.

            3. Hembuskan (8 Detik)
               Hembuskan napas perlahan melalui mulut dengan lembut.

            💡 Ulangi siklus ini sebanyak 4 kali hingga detak jantung dan pikiranmu lebih tenang.
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}