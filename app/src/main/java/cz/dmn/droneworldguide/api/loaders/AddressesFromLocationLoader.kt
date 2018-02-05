package cz.dmn.droneworldguide.api.loaders

import android.location.Address
import cz.dmn.droneworldguide.api.BaseLoader
import cz.dmn.droneworldguide.util.RxGeocoder
import io.reactivex.Observable
import javax.inject.Inject

class AddressesFromLocationLoader @Inject constructor(private val rxGeocoder: RxGeocoder) : BaseLoader<List<Address>>() {

    var latitude = 0.0
    var longitude = 0.0
    var maxResults = 1

    fun withLatitude(latitude: Double): AddressesFromLocationLoader {
        this.latitude = latitude
        return this
    }

    fun withLongitude(longitude: Double): AddressesFromLocationLoader {
        this.longitude = longitude
        return this
    }

    fun withMaxResults(maxResults: Int): AddressesFromLocationLoader {
        this.maxResults = maxResults
        return this
    }

    override fun buildObservable(): Observable<List<Address>> = rxGeocoder.getFromLocation(latitude, longitude, maxResults)
}