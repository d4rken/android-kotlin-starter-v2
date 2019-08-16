package eu.darken.androidkotlinstarter.common.vdc

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import eu.darken.androidkotlinstarter.App
import timber.log.Timber

abstract class VDC : ViewModel() {
    val TAG: String = App.logTag("VDC", javaClass.simpleName)

    init {
        Timber.tag(TAG).v("Initialized")
    }

    @CallSuper
    override fun onCleared() {
        Timber.tag(TAG).v("onCleared()")
        super.onCleared()
    }
}