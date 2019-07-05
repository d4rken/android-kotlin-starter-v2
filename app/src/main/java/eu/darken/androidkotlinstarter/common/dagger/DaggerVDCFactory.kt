package eu.darken.androidkotlinstarter.common.dagger

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import javax.inject.Inject
import javax.inject.Qualifier

class DaggerVDCFactory @Inject constructor(
        private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, VDCAssistedFactory<out ViewModel>>,
        owner: SavedStateRegistryOwner,
        @ViewStateDefaultArgs defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        @Suppress("UNCHECKED_CAST")
        return creators[modelClass]?.create(handle) as? T ?: throw IllegalStateException("Unknown VDC class")
    }
}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewStateDefaultArgs

@Module
internal abstract class VDCFactoryModule {
    @Binds
    abstract fun bindFactory(VDCFactory: DaggerVDCFactory): ViewModelProvider.Factory
}