package com.boilerplate.utils

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrintLogManager @Inject constructor(
    @ApplicationContext context: Context,
    enableLog: Boolean = true,
) {

    var enableLog = enableLog

    fun printMsg(msg: Any) {
        if (enableLog) {
            Log.e("Msg ==>", msg.toString())
        }
    }

    fun printMsg(tag: String, msg: String) {
        if (enableLog) {
            Log.e(tag, msg)
        }
    }

    fun printMsg(tag: String, msg: Any) {
        if (enableLog) {
            Log.e(tag, msg.toString())
        }
    }
}