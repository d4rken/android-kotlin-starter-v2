package eu.darken.androidkotlinstarter.main.core

import eu.darken.bb.common.dagger.PerApp
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerApp
class SomeRepo @Inject constructor() {
    private val counterStor = BehaviorSubject.createDefault(0L)

    fun resetTo(base: Long) {
        counterStor.onNext(base)
    }

    val testCounter: Observable<Long> = counterStor
            .switchMap { base ->
                Observable.interval(1, TimeUnit.SECONDS).map { it + base }
            }
            .cache()

    val testEmojis: Observable<String> = Observable.interval(200, TimeUnit.MILLISECONDS)
            .map { EMOJIS[(Math.random() * EMOJIS.size).toInt()] }
            .cache()

    companion object {
        internal val EMOJIS = listOf("\uD83D\uDE00", "\uD83D\uDE02", "\uD83E\uDD17", "\uD83D\uDE32")
    }
}
