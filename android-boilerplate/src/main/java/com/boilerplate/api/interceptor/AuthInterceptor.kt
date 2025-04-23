package com.boilerplate.api.interceptor


import android.content.Context
import com.android_boilerplate.R
import com.boilerplate.preference.CommonPreferenceManager
import com.boilerplate.utils.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val preferenceManager: CommonPreferenceManager,
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request.Builder = chain.request().newBuilder()
        if (preferenceManager.authToken.isNotEmpty()) {
            newRequest.addHeader(
                context.getString(R.string.authorization),
                preferenceManager.authToken
            )
        }
        newRequest.addHeader(Constant.ACCEPT, Constant.APPLICATION_JSON)
        return chain.proceed(newRequest.build())
    }
}