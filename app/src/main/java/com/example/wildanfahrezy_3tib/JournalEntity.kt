package com.example.wildanfahrezy_3tib

import androidx.room.Entity
import androidx.room.PrimaryKey

// Satu baris = satu halaman jurnal.
// mood disimpan sebagai angka urutan di MoodModel.all (0 = Senang ... 4 = Sedih)
@Entity(tableName = "journals")
class JournalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val mood: Int = 2,
    val createdAt: Long = System.currentTimeMillis()
)
