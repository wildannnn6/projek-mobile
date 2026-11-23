package com.example.wildanfahrezy_3tib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.wildanfahrezy_3tib.databinding.FragmentBreathingTechniqueBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BreathingTechniqueFragment : Fragment() {

    private var _binding: FragmentBreathingTechniqueBinding? = null
    private val binding get() = _binding!!

    // Job = "pekerjaan" coroutine yang sedang berjalan, disimpan supaya bisa dihentikan
    private var session: Job? = null
    private val totalCycles = 4

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreathingTechniqueBinding.inflate(inflater, container, false)
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

        // Tombol Mulai / Berhenti
        binding.btnStart.setOnClickListener {
            if (session?.isActive == true) {
                stopSession()
            } else {
                startSession()
            }
        }
    }

    // Latihan berpandu: hitung mundur tiap fase, diulang sebanyak totalCycles
    private fun startSession() {
        binding.btnStart.text = "Berhenti"
        session = viewLifecycleOwner.lifecycleScope.launch {
            for (cycle in 1..totalCycles) {
                binding.tvCycle.text = "Siklus $cycle dari $totalCycles"
                runPhase("Tarik napas perlahan lewat hidung", 4)
                runPhase("Tahan napas", 7)
                runPhase("Hembuskan perlahan lewat mulut", 8)
            }
            binding.tvCycle.text = "Latihan selesai"
            binding.tvCount.text = "✓"
            binding.tvPhase.text = "Bagus! Rasakan tubuhmu sekarang. Kamu bisa mencatat perasaanmu di jurnal."
            binding.btnStart.text = "Mulai Lagi"
        }
    }

    private fun stopSession() {
        session?.cancel()
        binding.tvCycle.text = "Siap memulai $totalCycles siklus"
        binding.tvCount.text = "-"
        binding.tvPhase.text = "Latihan dihentikan. Tekan Mulai kapan saja kamu siap."
        binding.btnStart.text = "Mulai"
    }

    // Satu fase: tampilkan petunjuk lalu hitung mundur detik demi detik
    private suspend fun runPhase(label: String, seconds: Int) {
        binding.tvPhase.text = label
        for (second in seconds downTo 1) {
            binding.tvCount.text = second.toString()
            delay(1000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        session?.cancel()
        _binding = null
    }
}
