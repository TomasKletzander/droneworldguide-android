package cz.dmn.droneworldguide.ui.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import com.facebook.CallbackManager
import cz.dmn.droneworldguide.R
import cz.dmn.droneworldguide.databinding.LoginActivityBinding
import cz.dmn.droneworldguide.ui.BaseViewModelActivity

class LoginActivity : BaseViewModelActivity<LoginViewModel>(LoginViewModel::class.java) {

    lateinit var binding: LoginActivityBinding
    val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity)

        //  Get view model
        binding.viewModel = viewModel

        //  Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //  Setup facebook login

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}