package com.example.wildanfahrezy_3tib.Message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.wildanfahrezy_3tib.R
import com.example.wildanfahrezy_3tib.databinding.FragmentHomeBinding
import com.example.wildanfahrezy_3tib.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private val messageList = listOf(
        MessageModel(
            "Ketenangan Diri",
            "Aku berhak beristirahat. Tidak semua hal harus kuselesaikan detik ini juga.",
            "https://picsum.photos/id/10/150/150"
        ),
        MessageModel(
            "Self-Love",
            "Aku bangga pada diriku yang terus bertahan dan belajar berkembang sejauh ini.",
            "https://picsum.photos/id/152/150/150"
        ),
        MessageModel(
            "Fokus & Belajar",
            "Proses belajarku berharga. Aku percaya pada kemampuan yang aku usahakan hari ini.",
            "https://picsum.photos/id/24/150/150"
        ),
        MessageModel(
            "Atasi Overthinking",
            "Kekhawatiran tidak mengubah masa depan. Hari ini aku memilih untuk fokus pada saat ini.",
            "https://picsum.photos/id/1015/150/150"
        ),
        MessageModel(
            "Semangat & Harapan",
            "Setiap hari baru membawa kesempatan baru untuk bertumbuh dan merasa bahagia.",
            "https://picsum.photos/id/1018/150/150"
        ),
        MessageModel(
            "Penerimaan Diri",
            "Tidak apa-apa jika merasa lelah. Yang terpenting adalah aku tidak berhenti berusaha.",
            "https://picsum.photos/id/28/150/150"
        ),
        MessageModel(
            "Kesehatan Mental",
            "Pikiranku adalah ruang yang damai. Aku memilih untuk melepaskan beban yang tak perlu.",
            "https://picsum.photos/id/1050/150/150"
        ),
        MessageModel(
            "Apresiasi Diri",
            "Setiap langkah kecil yang kuambil hari ini adalah sebuah kemajuan yang berarti.",
            "https://picsum.photos/id/1060/150/150"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for requireContext() fragment
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Afirmasi Positif"

        val adapter = MessageAdapter(requireContext(), messageList)
        binding.listMessageItems.adapter = adapter
    }
}