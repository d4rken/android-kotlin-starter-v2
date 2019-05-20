package eu.darken.androidkotlinstarter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import eu.darken.androidkotlinstarter.common.dagger.DaggerViewModelFactory
import eu.darken.androidkotlinstarter.common.dagger.ViewModelKey
import eu.darken.androidkotlinstarter.main.ui.MainActivityViewModel
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragmentViewModel

@Module
internal abstract class ViewModelBinder {
    @Binds
    abstract fun bindFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityVM(model: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExampleFragmentViewModel::class)
    abstract fun bindExampleFragmentVM(model: ExampleFragmentViewModel): ViewModel

}