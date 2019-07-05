package eu.darken.androidkotlinstarter.main.core

import eu.darken.androidkotlinstarter.AppComponent
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AppComponent.Scope
class SomeRepo @Inject constructor() {
    val testCounter: Observable<Long> = Observable.interval(1, TimeUnit.SECONDS)

    val testEmojis: Observable<String> = Observable.interval(200, TimeUnit.MILLISECONDS)
            .map { EMOJIS[(Math.random() * EMOJIS.size).toInt()] }

    val isShowFragment: Boolean
        get() = true


    companion object {
        internal val EMOJIS = listOf("\uD83D\uDE00", "\uD83D\uDE02", "\uD83E\uDD17", "\uD83D\uDE32")
    }
}
