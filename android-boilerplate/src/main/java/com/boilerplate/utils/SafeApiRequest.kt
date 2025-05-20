package com.boilerplate.utils

import android.app.Application
import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class SafeApiRequest(private val context: Context) {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        try {
            val response = call.invoke()
            val message = StringBuilder()

            if (response.isSuccessful) {
                return response.body()!!
            } else {
                if (response.code() == 401 || response.code() == 403) {
                    if (context is Application) {
                        throw AuthException("Session Expired")
                    } else {
                        throw ApiException("Something went wrong please try again!")
                    }
                } else {

                    val error = response.errorBody()?.string()
                    error?.let {
                        try {
                            val jonObj = JSONObject(it)
                            if (jonObj.has("message")) {
                                message.append(jonObj.getString("message"))
                            } else if (jonObj.has("Message")) {
                                message.append(jonObj.getString("Message"))
                            } else {
                                message.append("Something went wrong please try again!")
                            }
                        } catch (e: JSONException) {
                            message.append("\n")
                        }
                    }
                    throw ApiException(message.toString())

                }
            }
        } catch (ex: Exception) {
            if (ex is NoInternetException) {

            }
            throw ex
        }
    }
}