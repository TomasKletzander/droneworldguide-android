package cz.dmn.droneworldguide.ui.splash

import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionsListener(private val activity: SplashActivity) : MultiplePermissionsListener {

    private var rationaleShown = false

    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>, token: PermissionToken) {
        AllPermissionsRationaleDialog.with(activity)
                .withAcceptListener {
                    token.continuePermissionRequest()
                }
                .withRejectListener {
                    token.cancelPermissionRequest()
                }
                .show()
        rationaleShown = true
    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
        if (!report.areAllPermissionsGranted()) {
            if (rationaleShown) {
                activity.permissionsFinished()
            } else {
                MissingPermissionsDialog.with(activity)
                        .withAcceptListener {
                            activity.requestPermissions()
                        }
                        .withRejectListener {
                            activity.permissionsFinished()
                        }
                        .show()
            }
        } else {
            activity.permissionsFinished()
        }
    }
}