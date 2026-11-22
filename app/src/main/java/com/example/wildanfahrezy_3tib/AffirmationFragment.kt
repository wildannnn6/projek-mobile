package com.example.wildanfahrezy_3tib

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wildanfahrezy_3tib.R
import com.example.wildanfahrezy_3tib.databinding.FragmentAffirmationBinding

class AffirmationFragment : Fragment() {
    private var _binding: FragmentAffirmationBinding? = null
    private val binding get() = _binding!!
    private val messageList = listOf(
        AffirmationModel(
            "Ketenangan Diri",
            "Aku berhak beristirahat. Tidak semua hal harus kuselesaikan detik ini juga.",
            "https://picsum.photos/id/10/150/150"
        ),
        AffirmationModel(
            "Self-Love",
            "Aku bangga pada diriku yang terus bertahan dan belajar berkembang sejauh ini.",
            "https://picsum.photos/id/152/150/150"
        ),
        AffirmationModel(
            "Fokus & Belajar",
            "Proses belajarku berharga. Aku percaya pada kemampuan yang aku usahakan hari ini.",
            "https://picsum.photos/id/24/150/150"
        ),
        AffirmationModel(
            "Atasi Overthinking",
            "Kekhawatiran tidak mengubah masa depan. Hari ini aku memilih untuk fokus pada saat ini.",
            "https://picsum.photos/id/1015/150/150"
        ),
        AffirmationModel(
            "Semangat & Harapan",
            "Setiap hari baru membawa kesempatan baru untuk bertumbuh dan merasa bahagia.",
            "https://picsum.photos/id/1018/150/150"
        ),
        AffirmationModel(
            "Penerimaan Diri",
            "Tidak apa-apa jika merasa lelah. Yang terpenting adalah aku tidak berhenti berusaha.",
            "https://picsum.photos/id/28/150/150"
        ),
        AffirmationModel(
            "Kesehatan Mental",
            "Pikiranku adalah ruang yang damai. Aku memilih untuk melepaskan beban yang tak perlu.",
            "https://picsum.photos/id/1050/150/150"
        ),
        AffirmationModel(
            "Apresiasi Diri",
            "Setiap langkah kecil yang kuambil hari ini adalah sebuah kemajuan yang berarti.",
            "https://picsum.photos/id/1060/150/150"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAffirmationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Afirmasi Positif"

        val adapter = AffirmationAdapter(requireContext(), messageList)
        binding.listMessageItems.adapter = adapter

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.affirmation_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_tutorial -> {
                val intent = Intent(requireContext(), AffirmationTutorialActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
