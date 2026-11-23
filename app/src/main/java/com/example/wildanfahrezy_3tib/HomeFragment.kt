package com.example.wildanfahrezy_3tib

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.wildanfahrezy_3tib.databinding.FragmentHomeBinding
import com.example.wildanfahrezy_3tib.databinding.ItemMoodChoiceBinding
import com.example.wildanfahrezy_3tib.databinding.ItemMoodStatBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())

        showGreeting()
        showMoodQuickPick()
        showAffirmationOfTheDay()

        // Menu cepat
        binding.cardSelfCare.setOnClickListener {
            startActivity(Intent(requireContext(), SelfCareGuideActivity::class.java))
        }
        binding.cardEducation.setOnClickListener {
            startActivity(Intent(requireContext(), EducationActivity::class.java))
        }
        binding.cardHelp.setOnClickListener {
            startActivity(Intent(requireContext(), HelpActivity::class.java))
        }
        binding.btnMoreAffirmation.setOnClickListener {
            (requireActivity() as MainActivity).openTab(R.id.affirmation)
        }
    }

    // Dipanggil setiap halaman ini tampil lagi (misal setelah balik dari halaman tulis),
    // jadi ringkasan selalu mengikuti isi database terbaru.
    override fun onResume() {
        super.onResume()
        loadSummary()
    }

    private fun showGreeting() {
        val sharedPref = requireContext().getSharedPreferences("Mindsheet", Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", null)
            ?: sharedPref.getString("username", "teman")

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val salam = when {
            hour < 11 -> "Selamat pagi"
            hour < 15 -> "Selamat siang"
            hour < 18 -> "Selamat sore"
            else -> "Selamat malam"
        }
        binding.tvGreeting.text = "$salam, $name"

        val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        binding.tvDate.text = formatter.format(Date())
    }

    // Lima wajah mood. Ketuk salah satu -> langsung buka halaman tulis dengan mood terpilih.
    private fun showMoodQuickPick() {
        binding.llMoodQuick.removeAllViews()
        for (index in MoodModel.all.indices) {
            val mood = MoodModel.all[index]
            val item = ItemMoodChoiceBinding.inflate(layoutInflater, binding.llMoodQuick, false)
            item.ivMood.setImageResource(mood.iconRes)
            item.tvMood.text = mood.name
            item.root.setOnClickListener {
                val intent = Intent(requireContext(), WriteJournalActivity::class.java)
                intent.putExtra(WriteJournalActivity.EXTRA_MOOD, index)
                startActivity(intent)
            }
            binding.llMoodQuick.addView(item.root)
        }
    }

    // Afirmasi berganti setiap hari (berdasarkan nomor hari dalam setahun)
    private fun showAffirmationOfTheDay() {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val data = AffirmationModel.all[day % AffirmationModel.all.size]

        binding.imgAffIcon.setImageResource(data.iconRes)
        binding.tvAffCategory.text = data.category
        binding.tvAffMessage.text = data.message
        binding.cardAffirmation.setOnClickListener {
            startActivity(AffirmationDetailActivity.newIntent(requireContext(), data))
        }
    }

    private fun loadSummary() {
        viewLifecycleOwner.lifecycleScope.launch {
            val journals = db.journalDao().getAll()
            val total = journals.size

            // hitung jurnal 7 hari terakhir dan jumlah per mood
            val weekAgo = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000
            var thisWeek = 0
            val counts = IntArray(MoodModel.all.size)
            for (journal in journals) {
                if (journal.createdAt >= weekAgo) {
                    thisWeek++
                }
                val moodIndex = journal.mood.coerceIn(0, counts.size - 1)
                counts[moodIndex]++
            }

            binding.tvStatTotal.text = total.toString()
            binding.tvStatWeek.text = thisWeek.toString()

            // mood dominan = mood dengan jumlah terbanyak
            if (total == 0) {
                binding.ivStatMood.setImageResource(R.drawable.mood_neutral)
                binding.tvStatMood.text = "-"
            } else {
                var top = 0
                for (i in counts.indices) {
                    if (counts[i] > counts[top]) {
                        top = i
                    }
                }
                binding.ivStatMood.setImageResource(MoodModel.all[top].iconRes)
                binding.tvStatMood.text = MoodModel.all[top].name
            }

            // batang statistik per mood
            binding.llMoodStats.removeAllViews()
            if (total == 0) {
                binding.tvStatsEmpty.visibility = View.VISIBLE
            } else {
                binding.tvStatsEmpty.visibility = View.GONE
                for (i in counts.indices) {
                    val row = ItemMoodStatBinding.inflate(layoutInflater, binding.llMoodStats, false)
                    row.ivMood.setImageResource(MoodModel.all[i].iconRes)
                    row.tvName.text = MoodModel.all[i].name
                    row.progressMood.max = total
                    row.progressMood.progress = counts[i]
                    row.tvCount.text = counts[i].toString()
                    binding.llMoodStats.addView(row.root)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
