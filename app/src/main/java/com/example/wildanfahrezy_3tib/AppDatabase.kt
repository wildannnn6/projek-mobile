package com.example.wildanfahrezy_3tib

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// version naik jadi 2 karena tabel berubah (notes -> journals, ada kolom mood)
@Database(
    entities = [JournalEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // data versi lama dibuang saat struktur tabel berubah
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}
