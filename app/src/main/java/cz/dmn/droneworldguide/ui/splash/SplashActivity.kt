package cz.dmn.droneworldguide.ui.splash

import android.Manifest
import android.os.Bundle
import com.karumi.dexter.Dexter
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.ui.BaseActivity
import cz.dmn.droneworldguide.ui.MainNavigator
import dagger.Binds
import dagger.Module
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject lateinit var navigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        //  Request permissions if necessary
        requestPermissions()
    }

    fun requestPermissions() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(PermissionsListener(this))
                .check()
    }

    fun permissionsFinished() {
        navigator.startMain()
    }

    @Module
    abstract class InjectionModule {
        @Binds
        abstract fun bindActivity(activity: SplashActivity): BaseActivity
    }
}
