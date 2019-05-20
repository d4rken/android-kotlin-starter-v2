package eu.darken.androidkotlinstarter

import android.app.Activity

class ExampleApplicationMock : App() {

    fun setActivityComponentSource(injector: ManualInjector<Activity>) {
        this.activityInjector = injector
    }
}
