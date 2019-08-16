package eu.darken.androidkotlinstarter.common.dagger

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import eu.darken.androidkotlinstarter.App
import eu.darken.androidkotlinstarter.DaggerAppComponent

object AppInjector {
    fun init(app: App) {
        DaggerAppComponent.factory()
                .create(app)
                .inject(app)
        app
                .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = handleActivity(activity)

                    override fun onActivityStarted(activity: Activity) = Unit

                    override fun onActivityResumed(activity: Activity) = Unit

                    override fun onActivityPaused(activity: Activity) = Unit

                    override fun onActivityStopped(activity: Activity) = Unit

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) = Unit

                    override fun onActivityDestroyed(activity: Activity) = Unit
                })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is AutoInject) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
                            if (f is AutoInject) {
                                AndroidSupportInjection.inject(f)
                            }
                            super.onFragmentPreAttached(fm, f, context)
                        }
                    }, true)
        }
    }
}