package cz.dmn.droneworldguide.di

import cz.dmn.droneworldguide.DwgApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, ApplicationModule::class, ActivityBindingModule::class,
        DataModule::class, ViewModelModule::class))
interface ApplicationComponent {
    fun inject(application: DwgApplication)
}