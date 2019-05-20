package eu.darken.androidkotlinstarter.main.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.darken.androidkotlinstarter.main.core.SomeRepo
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
        private val someRepo: SomeRepo
) : ViewModel() {

    val state: MutableLiveData<State> = MutableLiveData(State(false))

    fun onGo() {
        state.postValue(state.value!!.copy(ready = true))
    }

    data class State(val ready: Boolean)
}