package com.lastblade.paypaycorpcurrencyexchanger.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRate
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest
class CurrencyRateDaoTest : BaseDaoTest() {

    @Test
    fun currencyRateInsert_returnTrue() = runTest {
        val hashMap = HashMap<String, Double>()
        hashMap["AED"] = 3.673
        hashMap["AFN"] = 89.012752
        hashMap["ALL"] = 112.7

        val currencyRate =
            CurrencyRate(base = "USD", timestamp = System.currentTimeMillis(), rates = hashMap)
        homeRepoImpl.insert(currencyRate)

        val currenciesFromDb = homeRepoImpl.dbAllRates()

        Truth.assertThat(currenciesFromDb.rates).containsKey("ALL")
        Truth.assertThat(currenciesFromDb.rates).hasSize(3)
    }

    @Test
    fun currencyRateDelete_returnTrue() = runTest {
        val hashMap = HashMap<String, Double>()
        hashMap["AED"] = 3.673
        hashMap["AFN"] = 89.012752
        hashMap["ALL"] = 112.7

        val currencyRate =
            CurrencyRate(base = "USD", timestamp = System.currentTimeMillis(), rates = hashMap)
        homeRepoImpl.insert(currencyRate)

        homeRepoImpl.deleteCurrencyRate()

        val currenciesRateFromDb = homeRepoImpl.dbAllRates()

        Truth.assertThat(currenciesRateFromDb).isNull()
    }
}