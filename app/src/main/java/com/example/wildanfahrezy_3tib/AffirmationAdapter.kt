package com.example.wildanfahrezy_3tib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.wildanfahrezy_3tib.databinding.ItemAffirmationBinding

class AffirmationAdapter(
    context: Context,
    private val affirmations: List<AffirmationModel>
) : ArrayAdapter<AffirmationModel>(context, 0, affirmations) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemAffirmationBinding.inflate(LayoutInflater.from(context), parent, false)
        val data = affirmations[position]

        // ikon kategori diambil dari drawable (tidak butuh internet)
        binding.imgIcon.setImageResource(data.iconRes)
        binding.textCategory.text = data.category
        binding.textMessage.text = data.message

        // ketuk kartu -> buka halaman detail
        binding.root.setOnClickListener {
            context.startActivity(AffirmationDetailActivity.newIntent(context, data))
        }
        return binding.root
    }
}
