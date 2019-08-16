package testhelper

import org.junit.After
import org.junit.Before
import timber.log.Timber


open class BaseTest {
    @Before
    open fun setup() {
        Timber.plant(JUnitTree())
    }

    @After
    open fun tearDown() {

    }
}
