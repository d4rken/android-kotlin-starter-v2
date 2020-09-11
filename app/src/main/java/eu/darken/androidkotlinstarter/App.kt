package eu.darken.androidkotlinstarter

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import eu.darken.androidkotlinstarter.common.dagger.AppInjector
import timber.log.Timber
import javax.inject.Inject


open class App : Application(), HasAndroidInjector {

    @Inject lateinit var appComponent: AppComponent
    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        AppInjector.init(this)

        Timber.tag(TAG).d("onCreate() done!")
    }

    companion object {
        internal val TAG = logTag("ExampleApp")

        fun logTag(vararg tags: String): String {
            val sb = StringBuilder()
            for (i in tags.indices) {
                sb.append(tags[i])
                if (i < tags.size - 1) sb.append(":")
            }
            return sb.toString()
        }
    }
}
