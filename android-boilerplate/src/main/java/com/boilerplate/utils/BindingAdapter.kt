package com.boilerplate.utils

import android.view.View
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.boilerplate.ext.hideShowViews

@BindingAdapter("setViewAdapter")
fun bindItemViewModels(viewPager2: ViewPager2, adapter: RecyclerView.Adapter<*>) {
    viewPager2.adapter = adapter
}

@BindingAdapter("setRecyclerAdapter")
fun bindItemViewModels(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.adapter = adapter
}

@BindingAdapter("visibility")
fun visibility(view: View, boolean: Boolean?) {
    hideShowViews(boolean ?: false, view)
}

@BindingAdapter("requestFocus")
fun requestFocus(view: View, isRequestFocus: Boolean?) {
    view.clearFocus()
    if (isRequestFocus == true) {
        view.requestFocus()
    }
}

@BindingAdapter("requestFocus")
fun requestFocus(view: MaterialTextView, isRequestFocus: Boolean?) {
    view.clearFocus()
    if (isRequestFocus == true) {
        view.parent.requestChildFocus(view, view)
    }
}

@BindingAdapter("requestFocus")
fun requestFocus(spinner: AppCompatSpinner, isRequestFocus: Boolean?) {
    if (isRequestFocus == true) {
        spinner.performClick()
    }
}

@BindingAdapter("errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: Int?) {
    if (errorMessage == null) {
        view.isErrorEnabled = false
    } else {
        view.error = errorMessage.let { view.context.getString(it) }
    }
}