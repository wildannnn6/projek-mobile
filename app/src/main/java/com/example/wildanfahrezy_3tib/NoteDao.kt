package com.example.wildanfahrezy_3tib

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    suspend fun getAll(): List<NoteEntity>

    // Ambil satu data berdasarkan id (dipakai halaman tulis jurnal)
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getById(id: Int): NoteEntity?

    @Insert
    suspend fun insert(note: NoteEntity)

    // Ubah data yang sudah ada (dicocokkan lewat primary key / id)
    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)
}
