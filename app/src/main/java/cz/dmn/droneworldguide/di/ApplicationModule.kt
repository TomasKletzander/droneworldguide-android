package cz.dmn.droneworldguide.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import cz.dmn.droneworldguide.DwgApplication
import cz.dmn.droneworldguide.MARKER_LOCAL_PHOTO
import cz.dmn.droneworldguide.MARKER_PLACE
import cz.dmn.droneworldguide.PREFS_MAP
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.util.toBitmap
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = arrayOf(AndroidInjectionModule::class))
class ApplicationModule(private val application: DwgApplication) {

    @Provides
    @Singleton
    fun provideDwgApplication() = application

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @Named(MARKER_PLACE)
    fun providePlaceMarker(): BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(loadTintedDrawable(R.drawable.map_marker, R.color.placeMarker))

    @Provides
    @Singleton
    @Named(MARKER_LOCAL_PHOTO)
    fun provideLocalPhotoMarker(): BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(loadTintedDrawable(R.drawable.tooltip_image, R.color.localPhotoMarker))

    @Provides
    @Singleton
    @Named(PREFS_MAP)
    fun provideMapPreferences(): SharedPreferences = application.getSharedPreferences(PREFS_MAP, Context.MODE_PRIVATE)

    private fun loadTintedDrawable(@DrawableRes drawableId: Int, @ColorRes colorId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(application, drawableId)
        drawable?.setColorFilter(ContextCompat.getColor(application, colorId), PorterDuff.Mode.SRC_ATOP)
        return drawable?.toBitmap()
    }
}
