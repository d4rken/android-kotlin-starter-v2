package eu.darken.androidkotlinstarter.main.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import eu.darken.androidkotlinstarter.common.dagger.VDCAssistedFactory
import eu.darken.androidkotlinstarter.common.dagger.VDCKey
import eu.darken.androidkotlinstarter.common.dagger.ViewStateDefaultArgs
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragment
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragmentModule

@Module
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @VDCKey(MainActivityVDC::class)
    abstract fun bindMainActivityVM(factory: MainActivityVDC.Factory): VDCAssistedFactory<out ViewModel>

    @Binds
    abstract fun bindSavedStateRegistryOwner(host: MainActivity): SavedStateRegistryOwner

    @Module
    companion object {
        @JvmStatic
        @Provides
        @ViewStateDefaultArgs
        internal fun provideDefaultArgs(): Bundle? {
            return null
        }
    }

    @ContributesAndroidInjector(modules = [ExampleFragmentModule::class])
    abstract fun exampleFragment(): ExampleFragment

}