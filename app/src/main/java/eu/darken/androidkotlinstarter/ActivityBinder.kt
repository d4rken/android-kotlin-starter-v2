package eu.darken.androidkotlinstarter

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.darken.androidkotlinstarter.main.ui.IntentModule
import eu.darken.androidkotlinstarter.main.ui.MainActivity
import eu.darken.androidkotlinstarter.main.ui.MainActivityModule


@Module
abstract class ActivityBinder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class, IntentModule::class])
    abstract fun mainActivity(): MainActivity
}