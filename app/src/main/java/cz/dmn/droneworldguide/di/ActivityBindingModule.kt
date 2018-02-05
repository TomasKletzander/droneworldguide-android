package cz.dmn.droneworldguide.di

import cz.dmn.droneworldguide.ui.localphoto.LocalPhotoActivity
import cz.dmn.droneworldguide.ui.localphotoinfo.LocalPhotoInfoFragment
import cz.dmn.droneworldguide.ui.login.LoginActivity
import cz.dmn.droneworldguide.ui.main.MainActivity
import cz.dmn.droneworldguide.ui.map.MapFragment
import cz.dmn.droneworldguide.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = arrayOf(SplashActivity.InjectionModule::class))
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = arrayOf(MainActivity.InjectionModule::class))
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeLocalPhotoActivity(): LocalPhotoActivity

    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment

    @ContributesAndroidInjector
    abstract fun contributeLocalPhotoInfoFragment(): LocalPhotoInfoFragment
}