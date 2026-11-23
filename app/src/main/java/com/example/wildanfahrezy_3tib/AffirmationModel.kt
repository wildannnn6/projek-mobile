package com.example.wildanfahrezy_3tib

data class AffirmationModel(
    val category: String,
    val message: String,
    val iconRes: Int
) {
    companion object {
        // dipakai bersama oleh halaman Afirmasi dan kartu "Afirmasi hari ini" di Beranda
        val all = listOf(
            AffirmationModel(
                "Ketenangan Diri",
                "Aku berhak beristirahat. Tidak semua hal harus kuselesaikan detik ini juga.",
                R.drawable.aff_leaf
            ),
            AffirmationModel(
                "Self-Love",
                "Aku bangga pada diriku yang terus bertahan dan belajar berkembang sejauh ini.",
                R.drawable.aff_heart
            ),
            AffirmationModel(
                "Fokus & Belajar",
                "Proses belajarku berharga. Aku percaya pada kemampuan yang aku usahakan hari ini.",
                R.drawable.aff_book
            ),
            AffirmationModel(
                "Atasi Overthinking",
                "Kekhawatiran tidak mengubah masa depan. Hari ini aku memilih untuk fokus pada saat ini.",
                R.drawable.aff_cloud
            ),
            AffirmationModel(
                "Semangat & Harapan",
                "Setiap hari baru membawa kesempatan baru untuk bertumbuh dan merasa bahagia.",
                R.drawable.aff_sun
            ),
            AffirmationModel(
                "Penerimaan Diri",
                "Tidak apa-apa jika merasa lelah. Yang terpenting adalah aku tidak berhenti berusaha.",
                R.drawable.aff_leaf
            ),
            AffirmationModel(
                "Kesehatan Mental",
                "Pikiranku adalah ruang yang damai. Aku memilih untuk melepaskan beban yang tak perlu.",
                R.drawable.aff_moon
            ),
            AffirmationModel(
                "Apresiasi Diri",
                "Setiap langkah kecil yang kuambil hari ini adalah sebuah kemajuan yang berarti.",
                R.drawable.aff_star
            )
        )
    }
}
