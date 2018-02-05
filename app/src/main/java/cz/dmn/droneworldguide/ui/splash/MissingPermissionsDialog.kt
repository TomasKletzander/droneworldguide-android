package cz.dmn.droneworldguide.ui.splash

import android.app.Activity
import android.support.v7.app.AlertDialog
import cz.dmn.droneworldguide.R

class MissingPermissionsDialog private constructor(private val activity: Activity) {

    companion object {
        fun with(activity: Activity) = MissingPermissionsDialog(activity)
    }

    lateinit var acceptListener: () -> Unit
    lateinit var rejectListener: () -> Unit

    fun withAcceptListener(acceptListener: () -> Unit): MissingPermissionsDialog {
        this.acceptListener = acceptListener
        return this
    }

    fun withRejectListener(rejectListener: () -> Unit): MissingPermissionsDialog {
        this.rejectListener = rejectListener
        return this
    }

    fun show() {
        AlertDialog.Builder(activity)
                .setTitle(R.string.missingPermissionsTitle)
                .setMessage(R.string.missingPermissionsMessage)
                .setPositiveButton(R.string.grant, { _, _ -> acceptListener() })
                .setNegativeButton(R.string._continue, { _, _ -> rejectListener() })
                .show()
    }
}
