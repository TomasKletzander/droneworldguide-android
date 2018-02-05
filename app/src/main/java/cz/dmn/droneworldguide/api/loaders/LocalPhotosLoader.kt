package cz.dmn.droneworldguide.api.loaders

import cz.dmn.droneworldguide.api.BaseLoader
import cz.dmn.droneworldguide.api.DataManager
import cz.dmn.droneworldguide.api.models.LocalPhoto
import io.reactivex.Observable
import javax.inject.Inject

class LocalPhotosLoader @Inject constructor(private val dataManager: DataManager) : BaseLoader<List<LocalPhoto>>() {

    override fun buildObservable(): Observable<List<LocalPhoto>> = dataManager.getLocalPhotos()
}