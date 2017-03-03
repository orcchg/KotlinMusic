package com.orcchg.dev.maxa.ktmusic.app.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import hugo.weaving.DebugLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor(private val context: Context) {

    /* Read & Write external storage */
    // --------------------------------------------------------------------------------------------
    @DebugLog
    fun hasReadExternalStoragePermission(): Boolean {
        return hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    @DebugLog
    fun hasWriteExternalStoragePermission(): Boolean {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    @DebugLog
    fun requestReadExternalStoragePermission(activity: Activity) {
        requestPermissions(activity, READ_EXTERNAL_STORAGE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    @DebugLog
    fun requestWriteExternalStoragePermission(activity: Activity) {
        requestPermissions(activity, WRITE_EXTERNAL_STORAGE_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    @DebugLog
    private fun hasPermission(permission: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true  // permissions automatically granted
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    @DebugLog
    private fun requestPermissions(activity: Activity, requestCode: Int, vararg permissions: String) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    companion object {
        val REQUEST_CODE_BASE = 101
        val READ_EXTERNAL_STORAGE_REQUEST_CODE = REQUEST_CODE_BASE       // 101;
        val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = REQUEST_CODE_BASE + 1  // 102;
    }
}
