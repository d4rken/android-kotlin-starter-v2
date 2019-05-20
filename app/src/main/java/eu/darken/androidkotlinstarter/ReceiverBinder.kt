package eu.darken.androidkotlinstarter

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.darken.androidkotlinstarter.main.core.receiver.ExampleReceiver

@Module
internal abstract class ReceiverBinder {
    @ContributesAndroidInjector
    internal abstract fun exampleReceiver(): ExampleReceiver

}