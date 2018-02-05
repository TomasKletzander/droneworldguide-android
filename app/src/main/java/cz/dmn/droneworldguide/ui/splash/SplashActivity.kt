package cz.dmn.droneworldguide.ui.splash

import android.Manifest
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.View
import com.karumi.dexter.Dexter
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.databinding.SplashActivityBinding
import cz.dmn.droneworldguide.ui.BaseActivity
import cz.dmn.droneworldguide.ui.MainNavigator
import dagger.Binds
import dagger.Module
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject lateinit var navigator: MainNavigator
    lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)

        //  Request permissions if necessary
        requestPermissions()
    }

    override fun onResume() {
        super.onResume()
        binding.root.animate().alpha(1f).translationY(0f).setDuration(1000).withEndAction {
            binding.progress.visibility = View.VISIBLE
            requestPermissions()
        }
    }

    fun requestPermissions() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(PermissionsListener(this))
                .check()
    }

    fun permissionsFinished() {
        binding.progress.visibility = View.INVISIBLE
        binding.root.animate().alpha(0f).translationY(resources.getDimensionPixelSize(R.dimen.splashFinalDistance).toFloat()).setDuration(1000).withEndAction {
            navigator.startMain()
        }
    }

    @Module
    abstract class InjectionModule {
        @Binds
        abstract fun bindActivity(activity: SplashActivity): BaseActivity
    }
}
