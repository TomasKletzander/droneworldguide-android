package cz.dmn.droneworldguide.api

import cz.dmn.droneworldguide.api.models.Place
import io.reactivex.Observable
import retrofit2.http.GET

interface DwgApi {
    @GET("places.json")
    fun getPlaces(): Observable<List<Place>>
}
