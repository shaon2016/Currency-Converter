package com.shaon2016.cleancodewithtest.data.network

import dagger.hilt.android.testing.HiltAndroidRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseApiTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

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
}