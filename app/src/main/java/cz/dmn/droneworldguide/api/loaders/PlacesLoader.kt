package cz.dmn.droneworldguide.api.loaders

import cz.dmn.droneworldguide.api.BaseLoader
import cz.dmn.droneworldguide.api.DataManager
import cz.dmn.droneworldguide.api.models.Place
import io.reactivex.Observable
import javax.inject.Inject

class PlacesLoader @Inject constructor(private val dataManager: DataManager) : BaseLoader<List<Place>>() {

    override fun buildObservable(): Observable<List<Place>> = dataManager.getPlaces()
}
