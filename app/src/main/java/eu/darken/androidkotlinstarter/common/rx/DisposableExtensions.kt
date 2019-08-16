package eu.darken.bb.common.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.withCompositeDisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

