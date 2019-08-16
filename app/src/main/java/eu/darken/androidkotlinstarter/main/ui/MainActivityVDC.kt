package eu.darken.androidkotlinstarter.main.ui

import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.darken.androidkotlinstarter.common.Stater
import eu.darken.androidkotlinstarter.common.vdc.SavedStateVDCFactory
import eu.darken.androidkotlinstarter.common.vdc.SmartVDC
import eu.darken.androidkotlinstarter.main.core.SomeRepo
import timber.log.Timber


class MainActivityVDC @AssistedInject constructor(
        @Assisted private val handle: SavedStateHandle,
        private val someRepo: SomeRepo
) : SmartVDC() {

    init {
        Timber.d("ViewModel: %s", this)
        Timber.d("SavedStateHandle: %s", handle)
        Timber.d("Persisted value: %s", handle.get<String>("key"))
        handle.set("key", "valueActivity")
    }

    private val stater = Stater(State())
    val state = stater.liveData

    fun onGo() {
        stater.update { it.copy(ready = true) }
    }

    data class State(val ready: Boolean = false)

    @AssistedInject.Factory
    interface Factory : SavedStateVDCFactory<MainActivityVDC>
}