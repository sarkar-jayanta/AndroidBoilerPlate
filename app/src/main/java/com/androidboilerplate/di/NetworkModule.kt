package com.androidboilerplate.di

import com.android_boilerplate.BuildConfig
import com.androidboilerplate.data.api.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkAppModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return ""
    }

    @Singleton
    @Provides
    fun providesApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideLoggingEnable(): Boolean = BuildConfig.DEBUG
}