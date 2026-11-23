package com.example.wildanfahrezy_3tib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wildanfahrezy_3tib.databinding.FragmentGroundingBinding

class GroundingFragment : Fragment() {

    private var _binding: FragmentGroundingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroundingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtJudul.text = "🌍 Grounding 5-4-3-2-1"
        binding.txtDeskripsi.text = """
            Teknik ini membawa pikiranmu kembali ke saat ini lewat kelima panca indera. Lakukan perlahan:

            5. Lihat
               Sebutkan 5 benda yang bisa kamu lihat di sekitarmu.

            4. Sentuh
               Rasakan 4 benda yang bisa kamu sentuh, misalnya kain baju atau permukaan meja.

            3. Dengar
               Dengarkan 3 suara, sejauh atau sedekat apa pun.

            2. Cium
               Temukan 2 aroma di sekitarmu.

            1. Kecap
               Rasakan 1 rasa di mulutmu, atau minum seteguk air.

            💡 Tarik napas pelan di antara setiap langkah.
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
