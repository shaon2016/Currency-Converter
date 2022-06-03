package com.lastblade.paypaycorpcurrencyexchanger.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lastblade.paypaycorpcurrencyexchanger.repo.home.HomeRepoImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
abstract class BaseDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var homeRepoImpl: HomeRepoImpl

    @Before
    fun setup() {
        hiltRule.inject()
    }

}