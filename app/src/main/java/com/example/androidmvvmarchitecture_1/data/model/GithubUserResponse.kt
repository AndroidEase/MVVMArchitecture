package com.example.androidmvvmarchitecture_1.data.model

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val items: List<User>
)

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
