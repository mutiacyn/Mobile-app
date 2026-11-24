package com.example.mutia.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mutia.Data.Dao.JadwalDao
import com.example.mutia.Data.Entity.JadwalEntity

@Database(
    entities = [JadwalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jadwalDao(): JadwalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "momcare_database"
                ).build().also { INSTANCE = it }
            }
    }
}