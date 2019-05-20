//package eu.darken.androidkotlinstarter.main.ui
//
//
//import android.app.Activity
//import androidx.test.InstrumentationRegistry
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.rule.ActivityTestRule
//import dagger.android.AndroidInjector
//import eu.darken.androidkotlinstarter.ExampleApplicationMock
//import eu.darken.androidkotlinstarter.R
//import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragment
//import eu.darken.mvpbakery.injection.ComponentSource
//import eu.darken.mvpbakery.injection.ManualInjector
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.ArgumentMatchers.any
//import org.mockito.Mock
//import org.mockito.Mockito.`when`
//import org.mockito.Mockito.doAnswer
//import org.mockito.junit.MockitoJUnit
//import org.mockito.junit.MockitoRule
//
//class MainActivityTest {
//
//    @get:Rule var mockitoRule: MockitoRule = MockitoJUnit.rule()
//
//    @get:Rule var activityRule = ActivityTestRule(MainActivity::class.java, true, false)
//
//    private lateinit var app: ExampleApplicationMock
//
//    @Mock lateinit var mainPresenter: MainActivityPresenter
//    @Mock lateinit var mainComponent: MainActivityComponent
//
//    @Mock lateinit var fragmentInjector: ComponentSource<androidx.fragment.app.Fragment>
//    @Mock lateinit var exampleFragmentPresemter: ExampleFragmentPresenter
//    @Mock lateinit var exampleFragmentComponent: ExampleFragmentComponent
//
//    @Before
//    fun setUp() {
//        app = InstrumentationRegistry.getTargetContext().applicationContext as ExampleApplicationMock
//        app.setActivityComponentSource(ActivityInjector())
//
//        doAnswer { invocation ->
//            val mainActivity = invocation.getArgument<MainActivity>(0)
//            mainActivity.fragmentInjector = fragmentInjector
//            null
//        }.`when`(mainComponent).inject(any())
//        `when`(mainComponent.presenter).thenReturn(mainPresenter)
//        `when`(mainPresenter.component).thenReturn(mainComponent)
//
//
//        doAnswer { invocation -> null }.`when`(exampleFragmentComponent).inject(any<ExampleFragment>())
//        `when`(fragmentInjector.get(any())).then { invocation -> exampleFragmentComponent }
//        `when`(exampleFragmentComponent.presenter).thenReturn(exampleFragmentPresemter)
//        `when`(exampleFragmentPresemter.component).thenReturn(exampleFragmentComponent)
//    }
//
//    inner class ActivityInjector : ManualInjector<Activity> {
//
//        override fun get(instance: Activity): AndroidInjector<Activity> {
//            @Suppress("UNCHECKED_CAST")
//            return mainComponent as AndroidInjector<Activity>
//        }
//
//        override fun inject(instance: Activity) {
//            mainComponent.inject(instance as MainActivity)
//        }
//
//    }
//
//    @Test
//    @Throws(Throwable::class)
//    fun checkFragmentShowing() {
//        activityRule.launchActivity(null)
//
//        activityRule.runOnUiThread { activityRule.activity.showExampleFragment() }
//
//        onView(withId(R.id.fab)).check(matches(isDisplayed()))
//    }
//}
