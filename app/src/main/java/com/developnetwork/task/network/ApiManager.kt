package com.developnetwork.task.network

import com.developnetwork.task.BuildConfig
import com.developnetwork.task.common.utils.UserPreference
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager {
    private var retrofit: Retrofit? = null
    private val REQUEST_TIMEOUT = 15
    private var okHttpClient: OkHttpClient? = null

    fun getClient(): Retrofit? {
        initOkHttp()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.baseUrl)
                .build()
        }
        return retrofit
    }

    private fun initOkHttp() {
        val httpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("Authorization", UserPreference.getUserToken()).build()
            chain.proceed(request)
        }
        okHttpClient = httpClient.build()
    }

}