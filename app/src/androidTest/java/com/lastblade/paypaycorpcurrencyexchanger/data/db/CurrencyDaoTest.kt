package com.lastblade.paypaycorpcurrencyexchanger.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.CurrenciesDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyDaoTest : BaseDaoTest() {

    private lateinit var currenciesDao: CurrenciesDao

    @Before
    fun init() {
        currenciesDao = db.currenciesDao()
    }


    @Test
    fun currencyInsert_returnTrue() = runTest {
        val hashMap = HashMap<String, String>()
        hashMap["BDT"] = "Bangladesh"

        val currency = Currencies(currencies = hashMap)
        currenciesDao.insert(currency)

        val currenciesFromDb = currenciesDao.allCurrencies()

        assertThat(currenciesFromDb.currencies).containsKey("BDT")
    }
    @Test
    fun currencyDelete_returnTrue() = runTest {
        val hashMap = HashMap<String, String>()
        hashMap["BDT"] = "Bangladesh"

        val currency = Currencies(currencies = hashMap)
        currenciesDao.insert(currency)
        currenciesDao.deleteAll()

        val currenciesFromDb = currenciesDao.allCurrencies()

        assertThat(currenciesFromDb).isNull()
    }
}