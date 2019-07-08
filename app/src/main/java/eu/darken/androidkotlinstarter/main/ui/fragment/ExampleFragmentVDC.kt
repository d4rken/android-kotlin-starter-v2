package eu.darken.androidkotlinstarter.main.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.darken.androidkotlinstarter.common.dagger.SavedStateVDCFactory
import eu.darken.androidkotlinstarter.main.core.SomeRepo
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class ExampleFragmentVDC @AssistedInject constructor(
        private val someRepo: SomeRepo,
        @Assisted private val handle: SavedStateHandle
) : ViewModel() {

    init {
        Timber.d("ViewModel: %s", this)
        Timber.d("SavedStateHandle: %s", handle)
        Timber.d("Persisted value: %s", handle.get<Long>("lastValue"))
        Timber.d("Default args: %s", handle.get<String>("fragmentArg"))
    }

    private val dataSource = PublishSubject.create<State>()
    private var dataSub: Disposable = createDataProcess()

    val state = dataSource
            .subscribeOn(Schedulers.computation())
            .toLiveData()

    private fun createDataProcess(): Disposable = Observables
            .combineLatest(someRepo.testEmojis, someRepo.testCounter)
            .map {
                handle.set("lastValue", it.second)
                return@map State(emoji = "${it.first} ${it.second}")
            }
            .subscribe { dataSource.onNext(it) }

    fun updateEmoji() {
        dataSub.dispose()
        dataSub = createDataProcess()
    }

    override fun onCleared() {
        dataSub.dispose()
        super.onCleared()
    }

    data class State(val emoji: String)

    fun <T> Observable<T>.toLiveData(): LiveData<T> {
        return LiveDataReactiveStreams.fromPublisher(this.toFlowable(BackpressureStrategy.ERROR))
    }

    @AssistedInject.Factory
    interface Factory : SavedStateVDCFactory<ExampleFragmentVDC>
}