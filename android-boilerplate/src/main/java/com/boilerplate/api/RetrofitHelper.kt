package com.boilerplate.api

import okhttp3.Interceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    fun <T> initialize(baseUrl: String, service: Class<T>, factoryList: List<Converter.Factory> = emptyList(), interceptorList: List<Interceptor> = emptyList()): T {

        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)

        interceptorList.forEach { okHttpClient.addInterceptor(it) }

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient.build())

        factoryList.forEach { retrofit.addConverterFactory(it) }

        return retrofit.build().create(service)
    }
}