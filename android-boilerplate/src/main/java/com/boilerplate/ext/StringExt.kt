package com.boilerplate.ext

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.boilerplate.utils.Constant

fun String.isValidEmail() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword() =
    !TextUtils.isEmpty(this) && this.length >= 6

fun String?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty() && this.trim { it <= ' ' }
    .isNotEmpty() && this.trim { it <= ' ' } != "null"

fun String?.string(): String =
    if (this.isNotNullOrEmpty()) this.toString() else ""

fun String.isDigitsOnly(): Boolean = this.all { it.isDigit() }

fun String?.capitalizeEveryWordFirstLetter(splitType: String = " "): String =
    this?.split(splitType)?.joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) } ?: ""

fun String.errorMessage(): String {
    return if (this.contains(Constant.LOCALHOST) || this.contains(Constant.SOCKET_TIMEOUT_EXCEPTION)) Constant.SOMETHING_WENT_WRONG else this
}

fun String?.error(): String = this?.errorMessage() ?: Constant.SOMETHING_WENT_WRONG

fun String.validateEnterMessage(): String {
    val error = String.format("%s %s", Constant.PLEASE_ENTER, this)
    return if (error.contains("*")) error.dropLast(1) else error
}

fun String.validateSelectMessage(): String {
    val error = String.format("%s %s", Constant.PLEASE_SELECT, this)
    return if (error.contains("*")) error.dropLast(1) else error
}

fun MutableLiveData<String?>.texts(): String {
    return this.value ?: ""
}

fun MutableLiveData<String>.text(): String {
    return this.value ?: ""
}

fun MutableLiveData<String>.textWithNull(): String? {
    return this.value
}

fun String?.text(): String {
    return this ?: ""
}