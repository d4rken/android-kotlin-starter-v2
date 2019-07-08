package eu.darken.androidkotlinstarter.main.ui.fragment

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import eu.darken.androidkotlinstarter.common.dagger.SavedStateVDCFactory
import eu.darken.androidkotlinstarter.common.dagger.VDCKey


@Module
abstract class ExampleFragmentModule {
    @Binds
    @IntoMap
    @VDCKey(ExampleFragmentVDC::class)
    abstract fun exampleFragmentVDC(factory: ExampleFragmentVDC.Factory): SavedStateVDCFactory<out ViewModel>
}

