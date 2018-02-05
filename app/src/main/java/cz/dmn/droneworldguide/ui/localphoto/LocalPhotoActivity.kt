package cz.dmn.droneworldguide.ui.localphoto

import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.databinding.LocalPhotoActivityBinding
import cz.dmn.droneworldguide.ui.BaseActivity

class LocalPhotoActivity : BaseActivity() {

    companion object {
        val EXTRA_PHOTO_PATH = "photoPath"
        val EXTRA_LOCATION = "location"
    }

    private lateinit var binding: LocalPhotoActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.local_photo_activity)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = intent.getStringExtra(EXTRA_LOCATION)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //  Hold activity transition until image is loaded
        ActivityCompat.postponeEnterTransition(this)

        //  Load image
        Glide
                .with(binding.photo)
                .load(intent.getStringExtra(EXTRA_PHOTO_PATH))
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        ActivityCompat.startPostponedEnterTransition(this@LocalPhotoActivity)
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        ActivityCompat.startPostponedEnterTransition(this@LocalPhotoActivity)
                        return false
                    }
                })
                .into(binding.photo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
