package eu.darken.androidkotlinstarter.common.vdc

interface SimpleVDCFactory<T : VDC> : VDCFactory<T> {
    fun create(): T
}