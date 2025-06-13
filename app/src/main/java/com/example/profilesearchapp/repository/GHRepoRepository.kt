package com.example.profilesearchapp.repository

import android.util.Log
import com.example.profilesearchapp.local.GHRepo
import com.example.profilesearchapp.local.GHRepoDao
import com.example.profilesearchapp.remote.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GHRepoRepository(private val repoDao: GHRepoDao) {

    suspend fun getRepos(): List<GHRepo> = withContext(Dispatchers.IO) {
        try {
            val response = NetworkService.apiService.searchRepositories()
            if (response.isSuccessful) {
                response.body()?.items?.let { repos ->
                    repoDao.clearAll()
                    repoDao.insertAll(repos)
                    return@withContext repos
                }
            }
        } catch (e: Exception) {
            Log.e("RepoRepository", "Error fetching from network", e)
        }
        // Return cached data on failure
        return@withContext repoDao.getAllRepositories()
    }

    suspend fun searchReposLocally(query: String): List<GHRepo> {
        return withContext(Dispatchers.IO) {
            repoDao.searchRepos(query)
        }
    }
}
