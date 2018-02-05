package cz.dmn.droneworldguide

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import cz.dmn.droneworldguide.di.ApplicationModule
import cz.dmn.droneworldguide.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class DwgApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}
