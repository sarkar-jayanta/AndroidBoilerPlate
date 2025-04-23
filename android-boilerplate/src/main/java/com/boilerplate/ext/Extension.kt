package com.boilerplate.ext

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.boilerplate.model.ErrorResponse
import com.boilerplate.model.ResponseModel
import com.boilerplate.utils.Constant
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

fun Activity.hideSoftKeyboard() {
    if (this.currentFocus != null) {
        try {
            val inputMethodManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

fun Context.checkNetwork(): Boolean {
    try {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = cm.activeNetwork
        if (network != null) {
            val networkCapabilities: NetworkCapabilities = cm.getNetworkCapabilities(network)!!
            return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            ))
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}


fun String.getFileName(): String? {
    return URLUtil.guessFileName(this, null, null)
}

fun Context.getFileName(uri: Uri): String? {
    var fileName: String? = null
    val contentResolver: ContentResolver = contentResolver

    if (uri.scheme == "content") {
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (columnIndex >= 0) {
                    fileName = cursor.getString(columnIndex)
                }
            }
        } finally {
            cursor?.close()
        }
    }

    if (fileName == null) {
        fileName = uri.path
        val cut = fileName?.lastIndexOf('/')
        if (cut != -1) {
            fileName = fileName?.substring(cut!! + 1)
        }
    }

    return fileName
}

val TEMP_IMAGE_FILE_NAME = "IMG_" + System.currentTimeMillis() + ".jpg"

/**
 * convert uri to file
 * */
fun Context.copyUriToFile(uri: Uri): File? {
    var `in`: InputStream? = null
    var out: OutputStream? = null
    var outFile: File? = null
    try {
        if (contentResolver != null) {
            `in` = contentResolver.openInputStream(uri)
            outFile = createImageFile(TEMP_IMAGE_FILE_NAME)
            if (outFile != null && `in` != null) {
                out = FileOutputStream(outFile)
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        // Ensure that the InputStreams are closed even if there's an exception.
        try {
            out?.close()

            // If you want to close the "in" InputStream yourself then remove this
            // from here but ensure that you close it yourself eventually.
            `in`?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return outFile
}

@Throws(IOException::class)
fun Context.createImageFile(imageFileName: String): File? {
    var storageDir = filesDir
    val dirCreated: Boolean
    if (storageDir == null) {
        val externalStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (externalStorage == null) {
            storageDir = File(cacheDir, Environment.DIRECTORY_PICTURES)
            dirCreated = storageDir.exists() || storageDir.mkdirs()
        } else {
            dirCreated = true
        }
    } else {
        storageDir = File(filesDir, Environment.DIRECTORY_PICTURES)
        dirCreated = storageDir.exists() || storageDir.mkdirs()
    }
    return if (dirCreated) {
        val imageFile = File(storageDir, imageFileName)
        var isDeleted = true
        if (imageFile.exists()) {
            isDeleted = imageFile.delete()
        }
        if (isDeleted) {
            val fileCreated = imageFile.createNewFile()
            if (fileCreated) imageFile else null
        } else {
            null
        }
    } else {
        null
    }
}

fun Context.convertInMultiPart(
    uri: Uri,
    imageKey: String
): MultipartBody.Part? {

    val file = copyUriToFile(uri)
    val reqFile = file?.asRequestBody("*/*".toMediaTypeOrNull())

    return if (reqFile != null) MultipartBody.Part.createFormData(
        imageKey, file.name, reqFile
    ) else null

}

fun String.createRequestBody(s: String): Pair<String, RequestBody> =
    this to s.toRequestBody("text/plain".toMediaTypeOrNull())

fun String.createRequestBody(): RequestBody = this.toRequestBody("text/plain".toMediaTypeOrNull())


inline fun <reified T> String.convertToModel(): T = Gson().fromJson(this, T::class.java)

inline fun <reified T> String.convertToList(): T =
    Gson().fromJson(this, object : TypeToken<T>() {}.type)

inline fun <reified T> T.convertToJson(): String = Gson().toJson(this)

fun Snackbar.withBackgroundColor(color: Int): Snackbar {
    this.setBackgroundTint(color)
    return this
}

fun Snackbar.withTextColor(color: Int): Snackbar {
    val tv: TextView = view.findViewById(com.google.android.material.R.id.snackbar_text)
    tv.setTextColor(color)
    return this
}

fun Snackbar.withGravity(gravity: Int): Snackbar {
    val params = view.layoutParams as FrameLayout.LayoutParams
    params.gravity = gravity
    view.layoutParams = params
    return this
}

inline fun <reified T> List<T>.spinnerIndex(predicate: (T) -> Boolean): Int {
    for ((index, item) in this.withIndex()) if (predicate(item))
        return index
    return 0
}

fun Int?.isNotNullOrEmpty(): Boolean = this != null && this != 0

fun ResponseBody?.errorMessage(): String {
    return try {
        this?.string()?.convertToModel<ErrorResponse<Any>>()?.message?.errorMessage()
            ?: Constant.SOMETHING_WENT_WRONG
    } catch (e: Exception) {
        Constant.SOMETHING_WENT_WRONG
    }
}

fun <T> ResponseModel<ArrayList<T>>?.list(): MutableList<T> {
    return this?.data ?: mutableListOf()
}