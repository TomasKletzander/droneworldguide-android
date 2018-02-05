package cz.dmn.droneworldguide.util

import android.app.Application
import android.location.Address
import android.location.Geocoder
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxGeocoder @Inject constructor(application: Application) {

    val geocoder = Geocoder(application)

    fun getFromLocation(latitide: Double, longitude: Double, maxResults: Int = 1): Observable<List<Address>> {
        return Observable.fromCallable<List<Address>> {
            geocoder.getFromLocation(latitide, longitude, maxResults)
        }
    }
}
