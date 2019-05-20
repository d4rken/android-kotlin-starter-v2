package eu.darken.androidkotlinstarter.common.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.darken.androidkotlinstarter.AppComponent
import javax.inject.Inject
import javax.inject.Provider

@AppComponent.Scope
class DaggerViewModelFactory @Inject constructor(
        private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    init {

    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}