package eu.darken.androidkotlinstarter.main.fragment

import androidx.lifecycle.SavedStateHandle
import eu.darken.androidkotlinstarter.main.core.SomeRepo
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragmentVDC
import io.kotlintest.eventually
import io.kotlintest.seconds
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.reactivex.subjects.BehaviorSubject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import testhelper.BaseTest
import testhelper.FakeMainThreadExtension

@ExtendWith(FakeMainThreadExtension::class)
class ExampleFragmentVDCTest : BaseTest() {

    @BeforeEach override fun setup() {
        super.setup()
    }

    @AfterEach override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun `test state update`() {
        val repo = mockk<SomeRepo>()
        val counterPub = BehaviorSubject.create<Long>()
        val emojiPub = BehaviorSubject.create<String>()
        every { repo.testCounter } returns counterPub
        every { repo.testEmojis } returns emojiPub
        val vdc = ExampleFragmentVDC(SavedStateHandle(), null, repo)

        vdc.state.value shouldBe null

        vdc.state.observeForever { }

        vdc.state.value shouldBe ExampleFragmentVDC.State("?")

        counterPub.onNext(55)
        emojiPub.onNext("cake")

        eventually(5.seconds) {
            vdc.state.value shouldBe ExampleFragmentVDC.State("cake 55")
        }
    }
}