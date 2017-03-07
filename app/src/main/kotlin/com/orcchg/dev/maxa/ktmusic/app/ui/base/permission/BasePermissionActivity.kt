package com.orcchg.dev.maxa.ktmusic.app.ui.base.permission

import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AlertDialog
import com.orcchg.dev.maxa.ktmusic.R
import com.orcchg.dev.maxa.ktmusic.app.manager.PermissionManager
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BaseActivity
import com.orcchg.dev.maxa.ktmusic.app.ui.base.MvpPresenter
import com.orcchg.dev.maxa.ktmusic.app.ui.base.MvpView
import hugo.weaving.DebugLog
import timber.log.Timber
import java.util.*

abstract class BasePermissionActivity<in V : MvpView, P : MvpPresenter<V>> : BaseActivity<V, P>() {

    private val dialog0: AlertDialog? = null

    @DebugLog
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Timber.i("onRequestPermissionsResult, requestCode = %s", requestCode)
        val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        if (granted) {
            when (requestCode) {
                PermissionManager.READ_EXTERNAL_STORAGE_REQUEST_CODE -> onPermissionGranted_readExternalStorage()
                PermissionManager.WRITE_EXTERNAL_STORAGE_REQUEST_CODE -> onPermissionGranted_writeExternalStorage()
            }
        } else {
            Timber.w("Permissions [%s] were not granted", Arrays.toString(permissions))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(permissions[0])) onPermissionDenied(requestCode)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog0?.dismiss()
    }

    /* Permissions handling */
    // --------------------------------------------------------------------------------------------
    /* Ask for permission */
    // ------------------------------------------
    protected fun askForPermission_readExternalStorage() {
        Timber.i("askForPermission_readExternalStorage")
        val pm = permissionManagerComponent.permissionManager()
        if (pm.hasReadExternalStoragePermission()) {
            onPermissionGranted_readExternalStorage()
        } else {
            pm.requestReadExternalStoragePermission(this)
        }
    }

    protected fun askForPermission_writeExternalStorage() {
        Timber.i("askForPermission_writeExternalStorage")
        val pm = permissionManagerComponent.permissionManager()
        if (pm.hasWriteExternalStoragePermission()) {
            onPermissionGranted_writeExternalStorage()
        } else {
            pm.requestWriteExternalStoragePermission(this)
        }
    }

    /* Permission granted */
    // ------------------------------------------
    protected fun onPermissionGranted_readExternalStorage() {
        // override in subclasses
    }

    protected fun onPermissionGranted_writeExternalStorage() {
        // override in subclasses
    }

    /* Permission denied */
    // ------------------------------------------
    protected fun onPermissionDenied(requestCode: Int) {
        Timber.i("onPermissionDenied: %s", requestCode)
        // open warning dialog by default
        val index = requestCode - PermissionManager.REQUEST_CODE_BASE
        val permissions = resources.getStringArray(R.array.permission_variants)
        var permission = "Unknown code=" + requestCode
        if (index >= 0 && index < permissions.size) permission = permissions[index]  // guard from index out of bounds
        val description = String.format(Locale.ENGLISH, resources.getString(R.string.permission_not_granted_message), permission)
        //        dialog0 = DialogProvider.showTextDialogTwoButtons(this, R.string.dialog_warning_title, description,
        //                R.string.button_settings, R.string.button_close,
        //                (dialog, which) -> {
        //                    dialog.dismiss();
        //                    navigationComponent.navigator().openSettings(this);
        //                },
        //                (dialog, which) -> dialog.dismiss());
    }
}
