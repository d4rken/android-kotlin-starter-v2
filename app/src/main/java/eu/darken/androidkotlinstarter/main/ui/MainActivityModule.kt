package eu.darken.androidkotlinstarter.main.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragment
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragmentModule

@Module(includes = [ExampleFragmentModule::class])
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [ExampleFragmentModule::class])
    abstract fun exampleFragment(): ExampleFragment

}