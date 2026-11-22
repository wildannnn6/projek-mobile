package com.example.wildanfahrezy_3tib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wildanfahrezy_3tib.databinding.FragmentSleepHygieneBinding

class SleepHygieneFragment : Fragment() {

    private var _binding: FragmentSleepHygieneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSleepHygieneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtJudul.text = "🌙 Panduan Sleep Hygiene (Tidur Nyenyak)"
        binding.txtDeskripsi.text = """
            Biasakan rutinitas ini sebelum tidur untuk kualitas istirahat yang maksimal:

            1. Bebas Layar (Digital Detox)
               Matikan atau jauhi smartphone minimal 30 menit sebelum berbaring.

            2. Redupkan Pencahayaan
               Gunakan lampu tidur yang redup untuk merangsang produksi hormon melatonin.

            3. Hindari Kafein Malam Hari
               Hindari konsumsi kopi atau teh pekat di atas jam 4 sore.

            🧘 Katakan pada diri sendiri: "Hari ini telah selesai, aku berhak beristirahat dengan tenang."
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}