package com.boilerplate.api.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.android_boilerplate.R
import com.boilerplate.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.InetAddress
import java.net.UnknownHostException

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException(applicationContext.getString(R.string.no_internet))
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                return try {
                    // Check if a known domain can be resolved
                    val address: InetAddress = InetAddress.getByName("google.com")
                    !address.equals("")
                } catch (e: UnknownHostException) {
                    false
                }
            }
        }
        return false
    }
}