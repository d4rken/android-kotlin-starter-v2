package eu.darken.androidkotlinstarter.common.rx

import eu.darken.androidkotlinstarter.common.Stater
import eu.darken.androidkotlinstarter.common.vdc.SmartVDC
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.withCompositeDisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun Disposable.withScopeVDC(vdc: SmartVDC) {
    vdc.addVdcDisp(this)
}

fun <T> Observable<T>.withScopeLiveData(stater: Stater<*>): () -> Disposable? {
    var disp: Disposable? = null
    stater.addLiveDataScopedDep {
        this.subscribe().also { disp = it }
    }
    return { disp }
}