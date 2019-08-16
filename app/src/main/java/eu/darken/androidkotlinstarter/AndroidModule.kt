package eu.darken.androidkotlinstarter

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.preference.PreferenceManager

import dagger.Module
import dagger.Provides
import eu.darken.bb.common.dagger.PerApp


@Module
class AndroidModule {

    @Provides
    @PerApp
    fun context(app: Application): Context = app.applicationContext

    @Provides
    @PerApp
    fun preferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @PerApp
    fun audioManager(context: Context): AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    @Provides
    @PerApp
    fun notificationManager(context: Context): NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}
