package com.example.androidmvvmarchitecture_1.data.api

import com.example.androidmvvmarchitecture_1.data.model.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserSearchResponse
}