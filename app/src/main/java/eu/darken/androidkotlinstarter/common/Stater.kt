package eu.darken.androidkotlinstarter.common

import androidx.lifecycle.LiveData
import eu.darken.androidkotlinstarter.common.rx.toLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class Stater<T> : HotData<T> {
    constructor(startValue: T) : super(startValue)
    constructor(startValueProvider: () -> T) : super(startValueProvider)

    private val liveDeps = mutableSetOf<() -> Disposable>()
    private var liveDepCompDis = CompositeDisposable()

    fun addLiveDataScopedDep(dep: () -> Disposable): Stater<T> = apply {
        synchronized(liveDeps) {
            liveDeps.add(dep)
        }
    }

    val liveData: LiveData<T> = data
            .doOnSubscribe {
                synchronized(liveDeps) {
                    liveDeps.forEach { liveDepCompDis.add(it.invoke()) }
                }
            }
            .doFinally {
                synchronized(liveDeps) {
                    liveDepCompDis.dispose()
                    liveDepCompDis = CompositeDisposable()
                }
            }
            .toLiveData()

}

