package com.example.profilesearchapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [GHRepo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): GHRepoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "github_repos.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}