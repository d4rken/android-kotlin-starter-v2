//package eu.darken.androidkotlinstarter.main.ui.fragment
//
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.espresso.matcher.ViewMatchers.withText
//import dagger.android.AndroidInjector
//import eu.darken.androidkotlinstarter.R
//import eu.darken.mvpbakery.injection.ManualInjector
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.ArgumentMatchers.any
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.junit.MockitoJUnit
//import org.mockito.junit.MockitoRule
//import testhelper.FragmentTestRule
//
//class ExampleFragmentTest {
//
//    @get:Rule var mockitoRule: MockitoRule = MockitoJUnit.rule()
//    @get:Rule var fragmentRule = FragmentTestRule(ExampleFragment::class.java)
//
//    @Mock lateinit var presenter: ExampleFragmentPresenter
//    @Mock lateinit var component: ExampleFragmentComponent
//
//
//    private val injector = object : ManualInjector<androidx.fragment.app.Fragment> {
//        override fun get(instance: androidx.fragment.app.Fragment): AndroidInjector<androidx.fragment.app.Fragment> {
//            @Suppress("UNCHECKED_CAST")
//            return component as AndroidInjector<androidx.fragment.app.Fragment>
//        }
//
//        override fun inject(fragment: androidx.fragment.app.Fragment) {
//
//        }
//    }
//
//    @Before
//    fun setUp() {
//        doAnswer { invocation ->
//            val exampleFragment = invocation.getArgument<ExampleFragment>(0)
//            exampleFragment.presenter = presenter
//            null
//        }.`when`(component).inject(any())
//        `when`(component.presenter).thenReturn(presenter)
//
//        doAnswer { invocationOnMock -> null }.`when`(presenter).onBindChange(any<ExampleFragmentPresenter.View>())
//        `when`(presenter.component).thenReturn(component)
//        fragmentRule.manualInjector = injector
//    }
//
//    @After
//    fun tearDown() {
//
//    }
//
//    @Test
//    @Throws(Throwable::class)
//    fun testShowEmoji() {
//        fragmentRule.launchActivity(null)
//        onView(withId(R.id.emoji_text)).check(matches(withText("Hello World!")))
//        fragmentRule.runOnUiThread { fragmentRule.fragment.showEmoji("test") }
//        onView(withId(R.id.emoji_text)).check(matches(withText("test")))
//    }
//
//    @Test
//    fun testFABClick() {
//        fragmentRule.launchActivity(null)
//
//        onView(withId(R.id.fab)).perform(click())
//        verify(presenter).onGetNewEmoji()
//    }
//
//}
