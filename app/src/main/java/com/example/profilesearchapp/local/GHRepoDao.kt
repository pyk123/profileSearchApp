package com.example.profilesearchapp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GHRepoDao {

    @Query("SELECT * FROM repositories")
    suspend fun getAllRepositories(): List<GHRepo>

    @Query("SELECT * FROM repositories WHERE id LIKE :query OR name LIKE '%' || :query || '%'")
    suspend fun searchRepos(query: String): List<GHRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<GHRepo>)

    @Query("DELETE FROM repositories")
    suspend fun clearAll()
}