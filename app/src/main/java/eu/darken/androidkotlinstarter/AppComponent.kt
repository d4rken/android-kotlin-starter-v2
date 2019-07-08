package eu.darken.androidkotlinstarter

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import eu.darken.androidkotlinstarter.common.dagger.AssistedInjectModule


@AppComponent.Scope
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

    @MustBeDocumented
    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
