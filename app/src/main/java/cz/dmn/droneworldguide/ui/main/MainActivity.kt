package cz.dmn.droneworldguide.ui.main

import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.LayoutInflater
import android.widget.ImageView
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.api.models.LocalPhoto
import cz.dmn.droneworldguide.databinding.MainActivityBinding
import cz.dmn.droneworldguide.databinding.NavigationMenuHeaderBinding
import cz.dmn.droneworldguide.ui.BaseActivity
import cz.dmn.droneworldguide.ui.MainNavigator
import cz.dmn.droneworldguide.ui.localphotoinfo.LocalPhotoInfoFragment
import cz.dmn.droneworldguide.ui.map.MapFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class MainActivity : BaseActivity(), MapFragment.Container, LocalPhotoInfoFragment.Container {

    lateinit var binding: MainActivityBinding
    lateinit var menuHeaderBinding: NavigationMenuHeaderBinding
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var localPhotoInfoFragment: LocalPhotoInfoFragment
    @Inject lateinit var navigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        //  Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //  Setup drawer toggle
        drawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close)
        drawerToggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(drawerToggle)

        //  Setup navigation menu
        menuHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.navigation_menu_header, binding.navigationMenu, false)
        binding.navigationMenu.addHeaderView(menuHeaderBinding.root)
        binding.navigationMenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionLogin -> navigator.startLogin()
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        //  Find local photo info fragment
        localPhotoInfoFragment = supportFragmentManager.findFragmentById(R.id.localPhotoInfo) as LocalPhotoInfoFragment
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.drawerLayout.removeDrawerListener(drawerToggle)
    }

    override fun showLocalPhotoInfo(localPhoto: LocalPhoto) = localPhotoInfoFragment.display(localPhoto)

    override fun isLocalPhotoInfoDisplayed(): Boolean = localPhotoInfoFragment.isDisplayed()

    override fun clearLocalPhotoInfo() = localPhotoInfoFragment.clear()

    override fun onAddToMap() {
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else if (localPhotoInfoFragment.isDisplayed()) {
            localPhotoInfoFragment.clear()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOpenPhoto(path: String, location: String, photo: ImageView) = navigator.startLocalPhoto(path, location, photo)

    @Module
    abstract class InjectionModule {

        @Binds
        abstract fun bindActivity(activity: MainActivity): BaseActivity
    }
}
