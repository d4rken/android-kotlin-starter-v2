package eu.darken.bb.common.rx

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun View.clicksDebounced(): Observable<Unit> {
    return this.clicks().throttleFirst(250, TimeUnit.MILLISECONDS)
}