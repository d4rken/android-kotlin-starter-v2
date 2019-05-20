package eu.darken.androidkotlinstarter.main.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.darken.androidkotlinstarter.main.core.SomeRepo
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ExampleFragmentViewModel @Inject constructor(
        private val someRepo: SomeRepo
) : ViewModel() {

    val state = MutableLiveData<State>(State(emoji = ""))
    private val emojiSub: Disposable

    init {
        emojiSub = someRepo.emoji
                .subscribeOn(Schedulers.computation())
                .delay(3, TimeUnit.SECONDS)
                .repeat()
                .doOnNext { Timber.d("Updating state with emoji: %s", it) }
                .subscribe { state.postValue(state.value!!.copy(emoji = it)) }
    }

    fun updateEmoji() {
        someRepo.emoji
                .subscribeOn(Schedulers.computation())
                .subscribe { emo: String -> state.postValue(state.value!!.copy(emoji = emo)) }
    }

    data class State(val emoji: String)

    override fun onCleared() {
        emojiSub.dispose()
        super.onCleared()
    }
}