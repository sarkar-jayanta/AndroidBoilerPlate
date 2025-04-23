package com.boilerplate.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.android_boilerplate.R
import com.boilerplate.ext.convertToJson
import com.boilerplate.ext.convertToList
import com.boilerplate.ext.convertToModel
import com.boilerplate.ext.isNotNullOrEmpty
import com.boilerplate.utils.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CommonPreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val AUTH_TOKEN = "AUTH_TOKEN"
    }

    private val masterKeyAlias: String by lazy { MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC) }

    val pref: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            String.format(
                "%s_%s", context.getString(R.string.app_name), Constant.SIT_COMMON
            ),
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    val editor: SharedPreferences.Editor = pref.edit()

    var authToken: String
        get() = getDataByKey(AUTH_TOKEN, "")
        set(fcm) = editor.putString(AUTH_TOKEN, fcm).apply()

    inline fun <reified T> setPref(key: String, value: T) {
        when (value) {
            is Boolean -> editor.putBoolean(key, (value as Boolean?)!!)
            is String -> editor.putString(key, value as String?)
            is Float -> editor.putFloat(key, (value as Float?)!!)
            is Long -> editor.putLong(key, (value as Long?)!!)
            is Int -> editor.putInt(key, (value as Int?)!!)
        }
        editor.apply()
    }

    inline fun <reified T> getPref(key: String, defaultValue: T? = null): T? {
        return when (defaultValue) {
            is Boolean -> {
                pref.getBoolean(key, defaultValue) as? T
            }

            is String -> {
                val ret = pref.getString(key, defaultValue as String?)
                ret as T?
            }

            is Float -> {
                val result = pref.getFloat(key, (defaultValue as Float?)!!)
                result as T?
            }

            is Long -> {
                val result = pref.getLong(key, (defaultValue as Long?)!!)
                result as T?
            }

            is Int -> {
                val result = pref.getInt(key, (defaultValue as Int?)!!)
                result as T?
            }

            else -> {
                return null
            }
        }
    }

    inline fun <reified T> setJsonPref(key: String, value: T) {
        editor.putString(key, value.convertToJson())
        editor.apply()
    }

    inline fun <reified T> getArrayJsonPref(key: String): T? {
        val getValue = getDataByKey(key)
        return if (getValue.isNotNullOrEmpty()) {
            getValue.convertToList()
        } else {
            null
        }
    }

    inline fun <reified T> getModelJsonPref(key: String): T? {
        val getValue = getDataByKey(key)
        return if (getValue.isNotNullOrEmpty()) {
            getValue.convertToModel()
        } else {
            null
        }
    }

    fun removePref(key: String) {
        editor.remove(key).apply()
    }

    fun removeAllPrefs() {
        editor.clear().apply()
    }

    @JvmOverloads
    fun getDataByKey(key: String, defaultValue: String = ""): String {
        return if (pref.contains(key)) {
            pref.getString(key, defaultValue).toString()
        } else {
            defaultValue
        }
    }
}