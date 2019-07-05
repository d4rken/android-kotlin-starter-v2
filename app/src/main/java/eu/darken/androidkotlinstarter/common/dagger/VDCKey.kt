package eu.darken.androidkotlinstarter.common.dagger

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class VDCKey(val value: KClass<out ViewModel>)