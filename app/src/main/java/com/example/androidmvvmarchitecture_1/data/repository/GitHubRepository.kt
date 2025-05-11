package com.example.androidmvvmarchitecture_1.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.androidmvvmarchitecture_1.data.api.GitHubApi
import com.example.androidmvvmarchitecture_1.data.model.User
import com.example.androidmvvmarchitecture_1.data.paging.UserPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val api: GitHubApi
) {
    fun searchUsers(query: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { UserPagingSource(api, query) }
        ).flow
    }
}
