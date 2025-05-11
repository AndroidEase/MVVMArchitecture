package com.example.androidmvvmarchitecture_1.data.api

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.example.androidmvvmarchitecture_1.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            throw IOException("No Internet Connection")
        }
        return chain.proceed(chain.request())
    }
}
