package com.boilerplate.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> AppCompatActivity.goToActivityAndClearTask(extras: Bundle.() -> Unit = {}) {
    hideSoftKeyboard()
    val intent = Intent(this, T::class.java)
    intent.putExtras(Bundle().apply(extras))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    finish()
}

inline fun <reified T : Activity> Fragment.goToActivityAndClearTask(extras: Bundle.() -> Unit = {}) {
    requireActivity().hideSoftKeyboard()
    val intent = Intent(requireContext(), T::class.java)
    intent.putExtras(Bundle().apply(extras))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    requireActivity().finish()
}