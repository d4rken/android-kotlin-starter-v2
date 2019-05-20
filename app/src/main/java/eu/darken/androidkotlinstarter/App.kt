package eu.darken.androidkotlinstarter

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import dagger.android.*
import eu.darken.androidkotlinstarter.common.dagger.AppInjector
import timber.log.Timber
import javax.inject.Inject


open class App : Application(), HasActivityInjector, HasServiceInjector, HasBroadcastReceiverInjector {

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

    @Inject lateinit var appComponent: AppComponent
    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var receiverInjector: DispatchingAndroidInjector<BroadcastReceiver>
    @Inject lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        AppInjector.init(this)

        Timber.tag(TAG).d("onCreate() done!")
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> = receiverInjector
}
