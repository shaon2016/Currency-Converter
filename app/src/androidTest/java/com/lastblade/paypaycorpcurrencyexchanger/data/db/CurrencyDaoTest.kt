package com.lastblade.paypaycorpcurrencyexchanger.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.CurrenciesDao
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@SmallTest
class CurrencyDaoTest : BaseDaoTest() {

    @Test
    fun currencyInsert_returnTrue() = runTest {
        val hashMap = HashMap<String, String>()
        hashMap["BDT"] = "Bangladesh"

        val currency = Currencies(currencies = hashMap)
        homeRepoImpl.insert(currency)

        val currenciesFromDb = homeRepoImpl.allCurrenciesDb()

        assertThat(currenciesFromDb.currencies).containsKey("BDT")
    }

    @Test
    fun currencyDelete_returnTrue() = runTest {
        val hashMap = HashMap<String, String>()
        hashMap["BDT"] = "Bangladesh"

        val currency = Currencies(currencies = hashMap)
        homeRepoImpl.insert(currency)
        homeRepoImpl.deleteCurrency()

        val currenciesFromDb = homeRepoImpl.allCurrenciesDb()

        assertThat(currenciesFromDb).isNull()
    }
}