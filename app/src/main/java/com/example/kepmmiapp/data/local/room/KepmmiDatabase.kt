package com.example.kepmmiapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kepmmiapp.data.local.entity.KegiatanEntity


@Database(
    entities = [KegiatanEntity::class],
    version = 1,
    exportSchema = false
)


abstract class KepmmiDatabase : RoomDatabase() {

    abstract fun kepmmiDao(): KepmmiDao
    abstract fun remoteKeysDao(): RemoteKeysDao


    companion object {
        @Volatile
        private var instance: KepmmiDatabase? = null

        fun getInstance(context: Context): KepmmiDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                KepmmiDatabase::class.java, "kepmmi.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }
}