package com.lastblade.paypaycorpcurrencyexchanger.view.fragment.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.lastblade.paypaycorpcurrencyexchanger.R
import com.lastblade.paypaycorpcurrencyexchanger.data.network.Helper.setResponse
import com.lastblade.paypaycorpcurrencyexchanger.view.activity.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
    }

    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun insertAmountIntoAmountTextField() {
        Espresso.onView(withId(R.id.evAmount)).perform(ViewActions.typeText("28.36"))
            .check(ViewAssertions.matches(ViewMatchers.withText("28.36")))
    }

    @Test
    fun insertAmountAndScrollToLastListPosition() {
        runTest {
            loadRequiredDataToHomeFragmentBeforeTest()

            Espresso.onView(withId(R.id.evAmount)).perform(ViewActions.typeText("28.36"))

            Espresso.onView(withId(R.id.rvExchangeRates)).perform(RecyclerViewActions.scrollToLastPosition<CurrencyExchangeRatesAdapter.CurrencyRatesViewHolder>())
        }
    }

    private fun loadRequiredDataToHomeFragmentBeforeTest() {
        // loading fake currencies
        mockWebServer.setResponse("sample_currency_list_response.json", 200)
        // loading fake currency rate
        mockWebServer.setResponse("sample_currency_rates_response.json", 200)
    }
}