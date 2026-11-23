package com.example.wildanfahrezy_3tib

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wildanfahrezy_3tib.databinding.ItemJournalBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JournalAdapter(
    private val journals: List<JournalEntity>,
    private val onClick: (JournalEntity) -> Unit,
    private val onDelete: (JournalEntity) -> Unit
) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    class JournalViewHolder(val binding: ItemJournalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JournalViewHolder {
        val binding = ItemJournalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return JournalViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: JournalViewHolder,
        position: Int
    ) {
        val journal = journals[position]

        // Judul kosong -> tulis "Tanpa judul"
        val title = if (journal.title.isNotBlank()) journal.title else "Tanpa judul"
        holder.binding.tvTitle.text = title
        holder.binding.tvPreview.text = journal.content

        // mood disimpan sebagai angka, diubah jadi gambar wajahnya
        holder.binding.ivMood.setImageResource(MoodModel.get(journal.mood).iconRes)

        // createdAt disimpan sebagai Long (milidetik), diubah jadi teks tanggal
        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        holder.binding.tvDate.text = formatter.format(Date(journal.createdAt))

        holder.binding.root.setOnClickListener { onClick(journal) }

        holder.binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Hapus Jurnal")
                .setMessage("Yakin ingin menghapus jurnal ini?")
                .setPositiveButton("Ya") { _, _ -> onDelete(journal) }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun getItemCount(): Int = journals.size
}
