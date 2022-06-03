package com.lastblade.paypaycorpcurrencyexchanger.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRateDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyRateDaoTest : BaseDaoTest() {
    private lateinit var currencyRateDao: CurrencyRateDao

    @Before
    fun init() {
        currencyRateDao = db.currencyRateDao()
    }


    @Test
    fun currencyRateInsert_returnTrue() = runTest {
        val hashMap = HashMap<String, Double>()
        hashMap["AED"] = 3.673
        hashMap["AFN"] = 89.012752
        hashMap["ALL"] = 112.7

        val currency =
            CurrencyRate(base = "USD", timestamp = System.currentTimeMillis(), rates = hashMap)
        currencyRateDao.insert(currency)

        val currenciesFromDb = currencyRateDao.all()

        Truth.assertThat(currenciesFromDb.rates).containsKey("ALL")
        Truth.assertThat(currenciesFromDb.rates).hasSize(3)
    }

    @Test
    fun currencyRateDelete_returnTrue() = runTest {
        val hashMap = HashMap<String, Double>()
        hashMap["AED"] = 3.673
        hashMap["AFN"] = 89.012752
        hashMap["ALL"] = 112.7

        val currency =
            CurrencyRate(base = "USD", timestamp = System.currentTimeMillis(), rates = hashMap)
        currencyRateDao.insert(currency)

        currencyRateDao.deleteAll()

        val currenciesFromDb = currencyRateDao.all()

        Truth.assertThat(currenciesFromDb).isNull()
    }
}