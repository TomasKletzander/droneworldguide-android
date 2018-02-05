package cz.dmn.droneworldguide.ui.login

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    var email = String()
    var password = String()
    val loginEnabled = ObservableBoolean(false)
    fun login() {

    }
}
