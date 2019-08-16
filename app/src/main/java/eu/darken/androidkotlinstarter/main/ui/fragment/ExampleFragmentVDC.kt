package eu.darken.androidkotlinstarter.main.ui.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.darken.androidkotlinstarter.common.Stater
import eu.darken.androidkotlinstarter.common.vdc.SmartVDC
import eu.darken.androidkotlinstarter.common.vdc.VDCFactory
import eu.darken.androidkotlinstarter.main.core.SomeRepo
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ExampleFragmentVDC @AssistedInject constructor(
        @Assisted private val handle: SavedStateHandle,
        @Assisted private val arguments: Bundle?,
        private val someRepo: SomeRepo
) : SmartVDC() {

    private val emojiObs: Observable<String> = Observables.combineLatest(someRepo.testEmojis, someRepo.testCounter)
            .subscribeOn(Schedulers.io())
            .map {
                handle.set("lastValue", it.second)
                "${it.first} ${it.second}"
            }
            .doOnNext { emojiVal ->
                stater.update { it.copy(emoji = emojiVal) }
            }

    private val stater = Stater(State())
            .addLiveDep { emojiObs.subscribe() }
    val state = stater.liveData

    init {
        Timber.d("ViewModel: %s", this)
        Timber.d("SavedStateHandle: %s", handle)
        Timber.d("Persisted value: %s", handle.get<Long>("lastValue"))
        Timber.d("Default args: %s", handle.get<String>("fragmentArg"))
    }

    fun updateEmoji() {
        someRepo.resetTo(0)
    }

    data class State(val emoji: String = "?")

    @AssistedInject.Factory
    interface Factory : VDCFactory<ExampleFragmentVDC> {
        fun create(handle: SavedStateHandle, arguments: Bundle?): ExampleFragmentVDC
    }
}