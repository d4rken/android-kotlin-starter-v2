package eu.darken.androidkotlinstarter.main.ui.fragment

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import eu.darken.androidkotlinstarter.common.vdc.VDC
import eu.darken.androidkotlinstarter.common.vdc.VDCFactory
import eu.darken.androidkotlinstarter.common.vdc.VDCKey


@Module
abstract class ExampleFragmentModule {
    @Binds
    @IntoMap
    @VDCKey(ExampleFragmentVDC::class)
    abstract fun exampleFragmentVDC(factory: ExampleFragmentVDC.Factory): VDCFactory<out VDC>
}

