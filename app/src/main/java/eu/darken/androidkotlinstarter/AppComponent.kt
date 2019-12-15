package eu.darken.androidkotlinstarter

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import eu.darken.androidkotlinstarter.common.dagger.AssistedInjectModule
import eu.darken.androidkotlinstarter.common.dagger.PerApp


@PerApp
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AssistedInjectModule::class,
    ServiceBinder::class,
    ReceiverBinder::class,
    AndroidModule::class,
    ActivityBinder::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>

}
