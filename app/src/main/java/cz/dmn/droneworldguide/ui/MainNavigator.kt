package cz.dmn.droneworldguide.ui

import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.widget.ImageView
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.ui.localphoto.LocalPhotoActivity
import cz.dmn.droneworldguide.ui.login.LoginActivity
import cz.dmn.droneworldguide.ui.main.MainActivity
import javax.inject.Inject

class MainNavigator @Inject constructor(private val activity: BaseActivity) {

    fun startMain() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun startLogin() {
        activity.startActivity(Intent(activity, LoginActivity::class.java))
    }

    fun startLocalPhoto(path: String, location: String, photo: ImageView) {
        activity.startActivity(Intent(activity, LocalPhotoActivity::class.java)
                .putExtra(LocalPhotoActivity.EXTRA_PHOTO_PATH, path)
                .putExtra(LocalPhotoActivity.EXTRA_LOCATION, location),
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, photo, "photo").toBundle())
    }
}
