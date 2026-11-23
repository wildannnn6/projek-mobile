package com.example.wildanfahrezy_3tib

// Pilihan mood untuk jurnal. Urutan list = angka yang disimpan di database.
data class MoodModel(
    val name: String,
    val iconRes: Int
) {
    companion object {
        val all = listOf(
            MoodModel("Senang", R.drawable.mood_happy),
            MoodModel("Tenang", R.drawable.mood_calm),
            MoodModel("Biasa", R.drawable.mood_neutral),
            MoodModel("Cemas", R.drawable.mood_anxious),
            MoodModel("Sedih", R.drawable.mood_sad)
        )

        // mood bawaan saat menulis jurnal baru: "Biasa"
        const val DEFAULT_INDEX = 2

        // ambil mood dari angka, dijaga supaya tidak keluar dari batas list
        fun get(index: Int): MoodModel = all[index.coerceIn(0, all.size - 1)]
    }
}
