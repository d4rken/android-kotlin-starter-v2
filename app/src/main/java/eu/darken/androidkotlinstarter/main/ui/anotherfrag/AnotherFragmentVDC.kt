package eu.darken.androidkotlinstarter.main.ui.anotherfrag

import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.darken.androidkotlinstarter.common.Stater
import eu.darken.androidkotlinstarter.common.vdc.SmartVDC
import eu.darken.androidkotlinstarter.common.vdc.VDCFactory
import eu.darken.androidkotlinstarter.main.core.SomeRepo

class AnotherFragmentVDC @AssistedInject constructor(
        @Assisted private val handle: SavedStateHandle,
        private val someRepo: SomeRepo
) : SmartVDC() {

    private val stater = Stater(
            ViewState(userName = "Matthias")
    )
    val state = stater.liveData

    fun updateUserName() {
        stater.update { oldState ->
            oldState.copy(userName = "Sebastian")
        }
    }

    data class ViewState(
            val userName: String
    )

    @AssistedInject.Factory
    interface Factory : VDCFactory<AnotherFragmentVDC> {
        fun create(handle: SavedStateHandle): AnotherFragmentVDC
    }
}