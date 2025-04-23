package com.boilerplate.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.SearchView

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun hideView(vararg hide: View) {
    hide.forEach { view -> view.hide() }
}

fun showView(vararg show: View) {
    show.forEach { view -> view.show() }
}

fun hideShowViews(bool: Boolean, vararg view: View) {
    view.onEach { if (bool) it.show() else it.hide() }
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun enableView(vararg enable: View) {
    enable.forEach { it.enable() }
}

fun disableView(vararg disable: View) {
    disable.forEach { it.disable() }
}

fun EditText.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun SearchView.clearSearch() {
    setQuery("", false)
    clearFocus()
}