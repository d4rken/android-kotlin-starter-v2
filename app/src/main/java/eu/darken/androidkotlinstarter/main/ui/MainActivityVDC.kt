package eu.darken.androidkotlinstarter.main.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.darken.androidkotlinstarter.common.dagger.VDCAssistedFactory
import eu.darken.androidkotlinstarter.main.core.SomeRepo
import timber.log.Timber


class MainActivityVDC @AssistedInject constructor(
        private val someRepo: SomeRepo,
        @Assisted private val handle: SavedStateHandle
) : ViewModel() {

    init {
        Timber.d("ViewModel: %s", this)
        Timber.d("SavedStateHandle: %s", handle)
        Timber.d("Persisted value: %s", handle.get<String>("key"))
        handle.set("key", "valueActivity")
    }

    val state: MutableLiveData<State> = MutableLiveData(State(false))

    fun onGo() {
        state.postValue(state.value!!.copy(ready = true))
    }

    data class State(val ready: Boolean)

    @AssistedInject.Factory
    interface Factory : VDCAssistedFactory<MainActivityVDC>
}