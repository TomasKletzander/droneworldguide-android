package cz.dmn.droneworldguide.ui.localphotoinfo

import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.api.BaseLoaderSubscriber
import cz.dmn.droneworldguide.api.loaders.AddressesFromLocationLoader
import cz.dmn.droneworldguide.api.models.LocalPhoto
import cz.dmn.droneworldguide.databinding.LocalPhotoInfoFragmentBinding
import cz.dmn.droneworldguide.util.onGlobalLayoutOnce
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LocalPhotoInfoFragment : DaggerFragment() {

    @Inject lateinit var addressesFromLocationLoader: AddressesFromLocationLoader
    lateinit var binding: LocalPhotoInfoFragmentBinding
    val container: Container?
    get() = context as Container
    private var displayed = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.local_photo_info_fragment, container, false)
        binding.root.onGlobalLayoutOnce {
            binding.root.translationY = binding.root.height.toFloat()
            binding.root.visibility = View.GONE
        }
        binding.location = ObservableField("")
        binding.setAddToMapListener {
            this.container?.onAddToMap()
        }
        binding.setShowPhotoListener {
            this.container?.onOpenPhoto(binding.imagePath!!, binding.location!!.get(), binding.thumbnail)
        }
        return binding.root
    }

    override fun onDestroyView() {
        addressesFromLocationLoader.dispose()
        super.onDestroyView()
    }

    fun display(localPhoto: LocalPhoto) {
        displayed = true
        binding.imagePath = localPhoto.path
        binding.timestamp = localPhoto.timestamp
        binding.executePendingBindings()
        binding.root.visibility = View.VISIBLE
        binding.root.animate().translationY(0f).setInterpolator(LinearOutSlowInInterpolator()).setDuration(100)
        addressesFromLocationLoader
                .withLatitude(localPhoto.latitude)
                .withLongitude(localPhoto.longitude)
                .execute(object : BaseLoaderSubscriber<List<Address>>() {
                    override fun onNext(addresses: List<Address>) {
                        if (!addresses.isEmpty()) {
                            binding.location?.set(addresses[0].getAddressLine(0))
                        }
                    }
                })
    }

    fun isDisplayed() = displayed

    fun clear() {
        displayed = false
        binding.root.animate()
                .translationY(binding.root.height.toFloat())
                .setInterpolator(LinearOutSlowInInterpolator())
                .setDuration(100)
                .withEndAction {
                    binding.root.visibility = View.GONE
                }
    }

    interface Container {
        fun onOpenPhoto(path: String, location: String, photo: ImageView)
        fun onAddToMap()
    }
}
