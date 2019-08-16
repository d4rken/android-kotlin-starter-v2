package eu.darken.androidkotlinstarter.common.vdc

import androidx.lifecycle.SavedStateHandle

interface SavedStateVDCFactory<T : VDC> : VDCFactory<T> {
    fun create(handle: SavedStateHandle): T
}