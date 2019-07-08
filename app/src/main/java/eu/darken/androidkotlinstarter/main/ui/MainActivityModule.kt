package eu.darken.androidkotlinstarter.main.ui

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import eu.darken.androidkotlinstarter.common.dagger.SavedStateVDCFactory
import eu.darken.androidkotlinstarter.common.dagger.VDCKey
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragment
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragmentModule

@Module
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @VDCKey(MainActivityVDC::class)
    abstract fun mainActivityVDC(factory: MainActivityVDC.Factory): SavedStateVDCFactory<out ViewModel>

    @ContributesAndroidInjector(modules = [ExampleFragmentModule::class])
    abstract fun exampleFragment(): ExampleFragment

}