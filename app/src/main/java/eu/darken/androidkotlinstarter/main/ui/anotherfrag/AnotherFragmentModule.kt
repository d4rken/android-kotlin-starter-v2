package eu.darken.androidkotlinstarter.main.ui.anotherfrag

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import eu.darken.androidkotlinstarter.common.vdc.VDC
import eu.darken.androidkotlinstarter.common.vdc.VDCFactory
import eu.darken.androidkotlinstarter.common.vdc.VDCKey


@Module
abstract class AnotherFragmentModule {
    @Binds
    @IntoMap
    @VDCKey(AnotherFragmentVDC::class)
    abstract fun anotherFragment(factory: AnotherFragmentVDC.Factory): VDCFactory<out VDC>
}

