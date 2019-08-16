package eu.darken.bb.common.rx

import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

class RetryWithDelay(
        private val maxRetries: Int = -1,
        private val delay: Long,
        private val timeUnit: TimeUnit = TimeUnit.MILLISECONDS
) : Function<Observable<out Throwable>, Observable<*>> {
    private var retryCount = 0

    override fun apply(attempts: Observable<out Throwable>): Observable<*> {
        return attempts.flatMap { throwable ->
            when {
                ++retryCount < maxRetries || maxRetries == -1 -> Observable.timer(delay, timeUnit)
                else -> Observable.error<Any>(throwable)
            }
        }
    }
}

fun <T> Observable<T>.retryWithDelay(delay: Long): Observable<T> {
    return this.retryWhen(RetryWithDelay(delay = delay))
}