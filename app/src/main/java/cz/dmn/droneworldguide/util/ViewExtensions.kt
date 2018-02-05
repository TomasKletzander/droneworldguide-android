package cz.dmn.droneworldguide.util

import android.view.View
import android.view.ViewTreeObserver

fun View.onGlobalLayoutOnce(call: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            call()
        }
    })
}
