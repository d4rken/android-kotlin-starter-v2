package eu.darken.androidkotlinstarter.common

import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class HotDataTest {

    @Test
    fun `test callback constructor`() {
        val callback = mockk<() -> String>()
        every { callback.invoke() } returns "testval"

        val hotData = HotData(callback)
        hotData.snapshot shouldBe "testval"
        verify { callback.invoke() }
    }

    @Test
    fun `test error while providing initial value`() {
        val callback = mockk<() -> String>()
        every { callback.invoke() } throws IllegalStateException()

        val hotData = HotData(callback)
        hotData.data.test().awaitDone(1, TimeUnit.SECONDS).assertError(IllegalStateException::class.java)
    }

    @Test
    fun `test value constructor`() {
        val hotData = HotData("strawberry")
        hotData.snapshot shouldBe "strawberry"
    }

    @Test
    fun `test close`() {
        val hotData = HotData("strawberry")
        val testSub = hotData.data.test()
        testSub.assertNotTerminated()
        hotData.close()
        testSub.assertNoErrors()
        testSub.assertComplete()
    }

    @Test
    fun `test init blocking constructor`() {
        val pub = PublishSubject.create<String>()
        val hotData = HotData { pub.blockingFirst() }

        val testSched = TestScheduler()
        val testSub = hotData.data.observeOn(testSched).timeout(1, TimeUnit.SECONDS, testSched).test()

        testSched.advanceTimeBy(10, TimeUnit.SECONDS)
        testSub.assertError(TimeoutException::class.java)

        pub.onNext("cake")
        hotData.data.test().awaitCount(1).assertValue("cake")
    }

    @Test
    fun `test updating`() {
        val testSched = TestScheduler()
        val hotData = HotData(
                initialValue = { "strawberry" },
                scheduler = testSched
        )
        testSched.triggerActions()
        hotData.snapshot shouldBe "strawberry"
        hotData.update {
            it shouldBe "strawberry"
            "apple"
        }
        testSched.triggerActions()
        hotData.snapshot shouldBe "apple"
    }

    @Test
    fun `test rx updating`() {
        val testSched = TestScheduler()
        val hotData = HotData(
                initialValue = { "strawberry" },
                scheduler = testSched
        )
        testSched.triggerActions()
        hotData.snapshot shouldBe "strawberry"
        val testSub = hotData.updateRx {
            it shouldBe "strawberry"
            "apple"
        }.test()
        testSched.triggerActions()
        hotData.snapshot shouldBe "apple"
        testSub.assertComplete().assertValue(HotData.Update("strawberry", "apple"))
    }

    @Test
    fun `test rx update failing`() {
        val testSched = TestScheduler()
        val hotData = HotData(
                initialValue = { "strawberry" },
                scheduler = testSched
        )
        testSched.triggerActions()
        hotData.snapshot shouldBe "strawberry"

        val error = IllegalArgumentException()
        val testSub = hotData.updateRx {
            it shouldBe "strawberry"
            throw error
        }.test()
        testSched.triggerActions()
        hotData.snapshot shouldBe "strawberry"
        testSub.assertError(error)
    }
}