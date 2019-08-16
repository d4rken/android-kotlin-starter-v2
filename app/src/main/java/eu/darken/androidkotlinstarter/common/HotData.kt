package eu.darken.androidkotlinstarter.common

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.Executors

open class HotData<T>(
        initialValue: () -> T,
        scheduler: Scheduler? = null
) {
    constructor(
            initialValue: () -> T
    ) : this(initialValue, null)

    constructor(
            initialValue: T
    ) : this({ initialValue }, null)

    private val scheduler = scheduler ?: Schedulers.from(Executors.newSingleThreadExecutor())

    private val updatePub = PublishSubject.create<(T) -> T>()
    private val statePub = BehaviorSubject.create<T>()

    init {
        Single
                .fromCallable { initialValue.invoke() }
                .subscribeOn(this.scheduler)
                .subscribe(
                        { value -> statePub.onNext(value) },
                        {
                            Timber.e(it, "Error while providing initial value.")
                            statePub.onError(it)
                        }
                )

        updatePub
                .observeOn(this.scheduler)
                .flatMap { action ->
                    statePub.take(1).map { oldState ->
                        val newState = action.invoke(oldState)
                        Timber.v("Update $oldState -> $newState")
                        when {
                            newState != null -> newState
                            else -> oldState
                        }
                    }
                }
                .subscribe(
                        { statePub.onNext(it) },
                        {
                            Timber.e(it, "Error while updating.")
                            statePub.onError(it)
                        }
                )
    }

    val snapshot: T
        get() = data.blockingFirst()

    val data: Observable<T> = statePub.hide()

    fun update(action: (T) -> T) {
        updatePub.onNext(action)
    }

    fun updateRx(action: (T) -> T): Single<Update<T>> = Single.create { emitter ->
        val wrap: (T) -> T = { oldValue ->
            try {
                val newValue = action.invoke(oldValue)
                emitter.onSuccess(Update(oldValue, newValue))
                newValue
            } catch (e: Throwable) {
                emitter.onError(e)
                oldValue
            }
        }
        update(wrap)
    }

    fun close() {
        updatePub.onComplete()
        statePub.onComplete()
    }

    data class Update<T>(val oldValue: T, val newValue: T)

}