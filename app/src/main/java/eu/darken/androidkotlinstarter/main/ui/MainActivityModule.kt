package eu.darken.androidkotlinstarter.main.ui

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import eu.darken.androidkotlinstarter.common.vdc.VDC
import eu.darken.androidkotlinstarter.common.vdc.VDCFactory
import eu.darken.androidkotlinstarter.common.vdc.VDCKey
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragment
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragmentModule

@Module
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @VDCKey(MainActivityVDC::class)
    abstract fun mainActivityVDC(factory: MainActivityVDC.Factory): VDCFactory<out VDC>

    @ContributesAndroidInjector(modules = [ExampleFragmentModule::class])
    abstract fun exampleFragment(): ExampleFragment

}