package eu.darken.androidkotlinstarter.common

import eu.darken.androidkotlinstarter.App
import eu.darken.androidkotlinstarter.common.rx.withCompositeDisposable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.rx2.await
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

    private val updatePub = PublishSubject.create<(T) -> T>().toSerialized()
    private val statePub = BehaviorSubject.create<T>().toSerialized()

    init {
        Single
                .fromCallable { initialValue.invoke() }
                .subscribeOn(this.scheduler)
                .doOnError { Timber.tag(TAG).e(it, "Error while providing initial value.") }
                .subscribe(
                        { value -> statePub.onNext(value) },
                        { statePub.onError(it) }
                )

        updatePub
                .observeOn(this.scheduler)
                .serialize()
                .concatMap { action ->
                    statePub.take(1).map { oldValue ->
                        val newValue = action.invoke(oldValue)
                        Timber.tag(TAG).v("Update $oldValue -> $newValue")
                        require(newValue != null) { "New value can't be NULL, oldvalue: $oldValue" }
                        newValue
                    }
                }
                .doOnError { Timber.tag(TAG).e(it, "Error while updating value.") }
                .subscribe(
                        { statePub.onNext(it) },
                        { statePub.onError(it) }
                )
    }

    val snapshot: T
        get() = data.blockingFirst()

    val data: Observable<T> = statePub.hide()

    fun update(action: (T) -> T) {
        updatePub.onNext(action)
    }

    suspend fun updateBlocking(action: (T) -> T): Update<T> {
        return updateRx(action).await()
    }

    /**
     * Guarantees that the updated data is visible to other subscribers when it is emitted
     */
    fun updateRx(action: (T) -> T): Single<Update<T>> = Single.create<Update<T>> { emitter ->
        val wrap: (T) -> T = { oldValue ->
            try {
                val newValue = action.invoke(oldValue)

                val compDisp = CompositeDisposable()

                val replayer = ReplaySubject.create<T>()
                replayer
                        .filter { it === newValue }
                        .take(1)
                        .doFinally { compDisp.dispose() }
                        .subscribe { emitter.onSuccess(Update(oldValue, newValue)) }
                        .withCompositeDisposable(compDisp)

                statePub
                        .doFinally { compDisp.dispose() }
                        .subscribe(
                                { replayer.onNext(it) },
                                { replayer.onError(it) },
                                { replayer.onComplete() }
                        )
                        .withCompositeDisposable(compDisp)

                emitter.setDisposable(compDisp)

                newValue
            } catch (e: Throwable) {
                emitter.tryOnError(e)
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

    companion object {
        private val TAG = App.logTag("HotData")
    }
}