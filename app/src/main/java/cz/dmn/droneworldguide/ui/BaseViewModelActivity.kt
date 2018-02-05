package cz.dmn.droneworldguide.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import javax.inject.Inject

abstract class BaseViewModelActivity<VM: ViewModel>(private val clazz: Class<VM>) : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var _viewModel: VM
    val viewModel: VM
    get() = _viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProviders.of(this, viewModelFactory).get(clazz)
    }
}