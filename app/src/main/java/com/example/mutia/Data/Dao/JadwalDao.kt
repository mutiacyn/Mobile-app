package com.example.mutia.Data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mutia.Data.Entity.JadwalEntity

@Dao
interface JadwalDao {
    @Query("SELECT * FROM jadwal ORDER BY dateKey ASC, jam ASC")
    suspend fun getAll(): List<JadwalEntity>

    @Insert
    suspend fun insert(jadwal: JadwalEntity)

    @Query("DELETE FROM jadwal WHERE id = :id")
    suspend fun deleteById(id: Int)
}