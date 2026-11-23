package com.example.wildanfahrezy_3tib

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface JournalDao {
    @Query("SELECT * FROM journals ORDER BY createdAt DESC")
    suspend fun getAll(): List<JournalEntity>

    // Cari kata kunci di judul ATAU isi jurnal
    @Query("SELECT * FROM journals WHERE title LIKE '%' || :keyword || '%' OR content LIKE '%' || :keyword || '%' ORDER BY createdAt DESC")
    suspend fun search(keyword: String): List<JournalEntity>

    // Ambil satu data berdasarkan id (dipakai halaman tulis jurnal)
    @Query("SELECT * FROM journals WHERE id = :id")
    suspend fun getById(id: Int): JournalEntity?

    @Query("SELECT COUNT(*) FROM journals")
    suspend fun count(): Int

    @Insert
    suspend fun insert(journal: JournalEntity)

    // Ubah data yang sudah ada (dicocokkan lewat primary key / id)
    @Update
    suspend fun update(journal: JournalEntity)

    @Delete
    suspend fun delete(journal: JournalEntity)

    @Query("DELETE FROM journals")
    suspend fun deleteAll()
}
