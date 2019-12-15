package eu.darken.androidkotlinstarter.common.vdc

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class SmartVDC : VDC() {
    private val vdcSubs = CompositeDisposable()

    fun addVdcDisp(disposable: Disposable) {
        vdcSubs.add(disposable)
    }

    override fun onCleared() {
        vdcSubs.dispose()
        super.onCleared()
    }
}