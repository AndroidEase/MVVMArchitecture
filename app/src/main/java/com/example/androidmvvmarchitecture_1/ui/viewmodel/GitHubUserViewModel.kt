package com.example.androidmvvmarchitecture_1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.androidmvvmarchitecture_1.data.model.User
import com.example.androidmvvmarchitecture_1.repository.GitHubRepository
import com.example.androidmvvmarchitecture_1.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class GitHubUserViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    fun searchUsers(query: String): Flow<PagingData<User>> {
        return repository.searchUsers(query).cachedIn(viewModelScope)
    }
}