package eu.darken.androidkotlinstarter.main.ui

import android.content.Intent
import dagger.Module
import dagger.Provides

@Module
class IntentModule {

    @Provides
    fun intent(mainActivity: MainActivity): Intent {
        return mainActivity.intent
    }

}