package cz.dmn.droneworldguide.ui.map

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import cz.dmn.droneworldguide.MARKER_LOCAL_PHOTO
import cz.dmn.droneworldguide.MARKER_PLACE
import cz.dmn.droneworldguide.PREFS_MAP
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.api.BaseLoaderSubscriber
import cz.dmn.droneworldguide.api.loaders.LocalPhotosLoader
import cz.dmn.droneworldguide.api.loaders.PlacesLoader
import cz.dmn.droneworldguide.api.models.LocalPhoto
import cz.dmn.droneworldguide.api.models.Place
import cz.dmn.droneworldguide.databinding.MapFragmentBinding
import dagger.Lazy
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class MapFragment : DaggerFragment() {

    companion object {
        const val PREF_LATITUDE = "latitude"
        const val PREF_LONGITUDE = "longitude"
        const val PREF_ZOOM = "zoom"
    }

    private lateinit var binding: MapFragmentBinding
    private var map: GoogleMap? = null
    @Inject lateinit var placesLoader: PlacesLoader
    @Inject lateinit var localPhotosLoader: LocalPhotosLoader
    @Inject @field:Named(MARKER_PLACE) lateinit var placeDescriptor: Lazy<BitmapDescriptor>
    @Inject @field:Named(MARKER_LOCAL_PHOTO) lateinit var localPhotoDescriptor: Lazy<BitmapDescriptor>
    @Inject @field:Named(PREFS_MAP) lateinit var prefs: SharedPreferences
    var container: Container? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        this.container = activity as Container
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync {
            initMap(it)
        }
        return binding.root
    }

    override fun onDestroyView() {
        placesLoader.dispose()
        localPhotosLoader.dispose()
        super.onDestroyView()
        binding.map.onDestroy()
        container = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    private fun initMap(map: GoogleMap) {
        this.map = map

        //  Setup map capabilities
        map.isIndoorEnabled = false
        map.isTrafficEnabled = false
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isTiltGesturesEnabled = false

        //  Restore last map position
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(prefs.getFloat(PREF_LATITUDE, 0f).toDouble(),
                prefs.getFloat(PREF_LONGITUDE, 0f).toDouble()), prefs.getFloat(PREF_ZOOM, 1f)))

        //  Setup map change listener
        map.setOnCameraIdleListener {
            prefs.edit()
                    .putFloat(PREF_LATITUDE, map.cameraPosition.target.latitude.toFloat())
                    .putFloat(PREF_LONGITUDE, map.cameraPosition.target.longitude.toFloat())
                    .putFloat(PREF_ZOOM, map.cameraPosition.zoom)
                    .apply()
        }

        //  Setup my location if permission granted
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }

        //  Setup markers behavior
        map.setOnMarkerClickListener {
            val tag = it.tag
            when (tag) {
                is LocalPhoto -> container?.showLocalPhotoInfo(tag)
            }
            true
        }
        map.setOnMapClickListener {
            if (container?.isLocalPhotoInfoDisplayed() ?: false) {
                container?.clearLocalPhotoInfo()
            }
        }

        //  Trigger data loaders
        loadPlaces()
        loadLocalPhotos()
    }

    private fun loadPlaces() {
        placesLoader.execute(object : BaseLoaderSubscriber<List<Place>>() {
            override fun onNext(places: List<Place>) {
                map?.apply {
                    places.forEach {
                        addMarker(MarkerOptions()
                                .icon(placeDescriptor.get())
                                .title(it.name)
                                .anchor(0.5f, 1f)
                                .position(LatLng(it.latitude, it.longitude)))
                    }
                }
            }
        })
    }

    private fun loadLocalPhotos() {
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            localPhotosLoader.execute(object : BaseLoaderSubscriber<List<LocalPhoto>>() {
                override fun onNext(localPhotos: List<LocalPhoto>) {
                    map?.apply {
                        localPhotos.forEach {
                            addMarker(MarkerOptions()
                                    .icon(localPhotoDescriptor.get())
                                    .title("Local photo")
                                    .anchor(0.5f, 1f)
                                    .position(LatLng(it.latitude, it.longitude))).tag = it
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    view?.let {
                        Snackbar.make(it, e.localizedMessage, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    interface Container {
        fun showLocalPhotoInfo(localPhoto: LocalPhoto)
        fun isLocalPhotoInfoDisplayed(): Boolean
        fun clearLocalPhotoInfo()
    }
}
