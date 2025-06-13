package com.example.profilesearchapp

import androidx.lifecycle.*
import com.example.profilesearchapp.local.GHRepo
import com.example.profilesearchapp.repository.GHRepoRepository

import kotlinx.coroutines.launch

class MainViewModel(private val repository: GHRepoRepository) : ViewModel() {

    private val _repos = MutableLiveData<List<GHRepo>>()
    val repos: LiveData<List<GHRepo>> = _repos
    val isLoading = MutableLiveData<Boolean>()
    fun fetchRepositories() {
        viewModelScope.launch {
            isLoading.value = true
            _repos.value = repository.getRepos()
            isLoading.value = false
        }
    }

    fun searchLocally(query: String) {
        viewModelScope.launch {
            isLoading.value = true
            _repos.value = repository.searchReposLocally(query)
            isLoading.value = false
        }
    }
}