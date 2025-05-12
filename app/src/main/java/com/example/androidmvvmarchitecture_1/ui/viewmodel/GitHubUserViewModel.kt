package com.example.androidmvvmarchitecture_1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.androidmvvmarchitecture_1.data.model.User
import com.example.androidmvvmarchitecture_1.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class GitHubUserViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    val users: Flow<PagingData<User>> = query
        .debounce(300)
        .filter { it.isNotBlank() }
        .distinctUntilChanged()
        .flatMapLatest {
            repository.searchUsers(it).catch { e ->
                _error.value = e.message ?: "Unknown error"
            }
        }
        .cachedIn(viewModelScope)

    fun setQuery(value: String) {
        query.value = value
    }
}