package cz.dmn.droneworldguide.api

import io.reactivex.observers.DisposableObserver

open class BaseLoaderSubscriber<T> : DisposableObserver<T>() {

    override fun onComplete() {
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
    }
}
