package eu.darken.androidkotlinstarter

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@AppComponent.Scope
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ServiceBinder::class,
    ReceiverBinder::class,
    AndroidModule::class,
    ActivityBinder::class,
    ViewModelBinder::class
])
interface AppComponent : AndroidInjector<App> {

    override fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    @MustBeDocumented
    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
