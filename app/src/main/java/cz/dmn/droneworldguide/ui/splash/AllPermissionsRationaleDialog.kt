package cz.dmn.droneworldguide.ui.splash

import android.app.Activity
import android.support.v7.app.AlertDialog
import cz.dmn.droneworldguide.R

class AllPermissionsRationaleDialog private constructor(private val activity: Activity) {

    companion object {
        fun with(activity: Activity) = AllPermissionsRationaleDialog(activity)
    }

    lateinit var acceptListener: () -> Unit
    lateinit var rejectListener: () -> Unit

    fun withAcceptListener(acceptListener: () -> Unit): AllPermissionsRationaleDialog {
        this.acceptListener = acceptListener
        return this
    }

    fun withRejectListener(rejectListener: () -> Unit): AllPermissionsRationaleDialog {
        this.rejectListener = rejectListener
        return this
    }

    fun show() {
        AlertDialog.Builder(activity)
                .setTitle(R.string.allPermissionsRationaleTitle)
                .setMessage(R.string.allPermissionsRationaleMessage)
                .setPositiveButton(R.string.ok, { _, _ -> acceptListener() })
                .setNegativeButton(R.string.no, { _, _ -> rejectListener() })
                .show()
    }
}