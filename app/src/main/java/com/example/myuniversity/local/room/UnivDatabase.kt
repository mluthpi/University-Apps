package com.example.myuniversity.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myuniversity.local.room.entity.UnivEntity


@Database(entities = [UnivEntity::class], version = 1, exportSchema = false)
abstract class UnivDatabase : RoomDatabase() {
    abstract fun univDao():UnivDao

    companion object {
        @Volatile
        private var instance: UnivDatabase? = null
        fun getInstance(context: Context): UnivDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UnivDatabase::class.java,"Univ.db"
                ).build()
            }
    }
}