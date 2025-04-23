package com.boilerplate.ext

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

const val requestStorage = Manifest.permission.READ_EXTERNAL_STORAGE
const val requestCamera = Manifest.permission.CAMERA

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
const val requestNotification = Manifest.permission.POST_NOTIFICATIONS

fun Fragment.requestPermissions(
    request: ActivityResultLauncher<Array<String>>,
    permissions: Array<String>
) = request.launch(permissions)

fun Fragment.isAllPermissionsGranted(permissions: Array<String>) = permissions.all {
    ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
}

fun Map<String, Boolean>.checkAllPermissionGranted() = this.containsValue(false).not()

fun Fragment.checkPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED

fun Activity.checkPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Context.checkPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Activity.storagePermission(): Boolean =
    Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU && checkPermissionGranted(requestStorage)

fun Activity.cameraPermission(): Boolean = checkPermissionGranted(requestCamera)