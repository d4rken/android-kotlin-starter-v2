package eu.darken.androidkotlinstarter

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.darken.androidkotlinstarter.main.core.service.ExampleService


@Module
internal abstract class ServiceBinder {
    @ContributesAndroidInjector
    internal abstract fun exampleService(): ExampleService
}