package com.example.wildanfahrezy_3tib

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.wildanfahrezy_3tib.databinding.ItemAffirmationBinding
import com.google.android.material.snackbar.Snackbar

class AffirmationAdapter(
    context: Context,
    private val messages: List<AffirmationModel>
) : ArrayAdapter<AffirmationModel>(context, 0, messages) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemAffirmationBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root
        val data = messages[position]
// Muat gambar dari URL ke ImageView
        Glide.with(context)
            .load(data.avatarUrl)
            .into(binding.avatarImg)

        binding.textSender.text = data.senderName
        binding.textMessage.text = data.messageText

        view.setOnClickListener {
            val i = Intent(context, AffirmationDetailActivity::class.java)
            i.putExtra("SENDER", data.senderName)
            i.putExtra("PESAN", data.messageText)
            context.startActivity(i)
//            Snackbar.make(
//                parent,
//                "Pesan dari ${data.senderName}: ${data.messageText}",
//                Snackbar.LENGTH_SHORT
//            ).show()
        }
        return view
    }
}