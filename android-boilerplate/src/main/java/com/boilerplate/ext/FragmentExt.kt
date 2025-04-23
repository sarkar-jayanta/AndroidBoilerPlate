package com.boilerplate.ext

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

fun Fragment.onBackPressedCustomAction(action: () -> Unit) {
    activity?.onBackPressedDispatcher?.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override
            fun handleOnBackPressed() {
                action()
            }
        })
}

fun Fragment.navi(@IdRes resId: Int) {
    requireActivity().hideSoftKeyboard()
    findNavController().navigate(resId)
}

fun Fragment.navi(@IdRes resId: Int, option: Bundle?) {
    requireActivity().hideSoftKeyboard()
    findNavController().navigate(resId, option)
}

fun Fragment.naviBack() {
    findNavController().navigateUp()
}

fun Fragment.naviPop(@IdRes resId: Int, option: Bundle?) {
    findNavController().navigateUp()
    requireActivity().hideSoftKeyboard()
    findNavController().navigate(resId, option)
}

/**
 *  Navigate to the destination with the new bundle
 * */
fun Fragment.naviFragmentInBackStack(
    destinationId: Int,
    @IdRes resId: Int? = null,
    newBundle: Bundle? = null
) {
    try {
        findNavController().getBackStackEntry(destinationId)
        findNavController().popBackStack(destinationId, false)

        if (newBundle != null) {
            findNavController().popBackStack()
            findNavController().navigate(destinationId, newBundle)
        }
    } catch (e: Exception) {
        if (resId != null)
            navi(resId, newBundle)
    }
}

/**
 *  Navigate to the destination with the new bundle
 * */
fun NavController.naviFragmentInBackStack(
    destinationId: Int,
    @IdRes resId: Int? = null,
    newBundle: Bundle? = null
) {
    try {
        getBackStackEntry(destinationId)
        popBackStack(destinationId, false)

        if (newBundle != null) {
            popBackStack()
            navigate(destinationId, newBundle)
        }
    } catch (e: Exception) {
        if (resId != null)
            navigate(resId, newBundle)
    }
}