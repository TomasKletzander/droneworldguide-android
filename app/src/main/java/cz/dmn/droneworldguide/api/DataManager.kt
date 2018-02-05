package cz.dmn.droneworldguide.api

import android.app.Application
import android.media.ExifInterface
import android.os.Environment
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.api.models.LocalPhoto
import cz.dmn.droneworldguide.api.models.Place
import cz.dmn.droneworldguide.util.appendPath
import io.reactivex.Observable
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(private val dwgApi: DwgApi,
                                      private val application: Application) {

    companion object {
        val sdfTimestamp = SimpleDateFormat("dd.MM.yyyy  HH:mm")
    }

    fun getPlaces(): Observable<List<Place>> = dwgApi.getPlaces()

    fun getLocalPhotos(): Observable<List<LocalPhoto>> {
        return Observable.fromCallable {
            //  Try to find local photos folder
            val file = Environment.getExternalStorageDirectory().appendPath("DJI").appendPath("dji.go.v4").appendPath("DJI Album")
            if (!file.exists()) {
                throw IOException(application.getString(R.string.djiPhotosNotFound))
            }
            val photos = file.listFiles { dir, name ->
                name.endsWith(".jpg", true)
            } ?: emptyArray<File>()
            val localPhotos = mutableListOf<LocalPhoto>()
            photos.forEach {
                loadLocalPhoto(it)?.let { localPhotos.add(it) }
            }
            localPhotos
        }
    }

    private fun loadLocalPhoto(file: File): LocalPhoto? {
        try {
            val exifIfc = ExifInterface(file.absolutePath)
            val floats = FloatArray(2)
            if (!exifIfc.getLatLong(floats)) {
                return null
            }
            return LocalPhoto(floats[0].toDouble(), floats[1].toDouble(), file.absolutePath, exifIfc.getAttribute(ExifInterface.TAG_DATETIME))
        } catch (t: Throwable) {
            return null
        }
    }
}
