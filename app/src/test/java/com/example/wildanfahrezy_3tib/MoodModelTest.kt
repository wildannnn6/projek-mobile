package com.example.wildanfahrezy_3tib

import org.junit.Assert.assertEquals
import org.junit.Test

// Unit test untuk MoodModel.get(): angka mood dari database harus selalu aman dipakai
class MoodModelTest {

    @Test
    fun jumlahMood_limaPilihan() {
        assertEquals(5, MoodModel.all.size)
    }

    @Test
    fun get_indexNormal_mengembalikanMoodYangBenar() {
        assertEquals("Senang", MoodModel.get(0).name)
        assertEquals("Sedih", MoodModel.get(4).name)
    }

    @Test
    fun get_indexDiLuarBatas_tidakCrash() {
        // terlalu kecil -> mood pertama, terlalu besar -> mood terakhir
        assertEquals("Senang", MoodModel.get(-1).name)
        assertEquals("Sedih", MoodModel.get(99).name)
    }

    @Test
    fun moodBawaan_adalahBiasa() {
        assertEquals("Biasa", MoodModel.get(MoodModel.DEFAULT_INDEX).name)
    }
}
