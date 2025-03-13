package com.example.kepmmiapp.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kepmmiapp.data.local.entity.KegiatanEntity

@Dao
interface KepmmiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKegiatan(kegiatan: List<KegiatanEntity>)

    @Query("SELECT * FROM kegiatan ORDER BY created_at DESC")
    fun getKegiatan(): PagingSource<Int, KegiatanEntity>


    @Query("DELETE FROM kegiatan")
    suspend fun deleteAll()


}