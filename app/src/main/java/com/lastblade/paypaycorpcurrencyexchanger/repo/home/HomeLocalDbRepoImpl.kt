package com.lastblade.paypaycorpcurrencyexchanger.repo.home

import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.CurrenciesDao
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRateDao
import com.lastblade.paypaycorpcurrencyexchanger.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeLocalDbRepoImpl @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val currencyRateDao: CurrencyRateDao,
    @ApplicationScope private val externalScope: CoroutineScope,
) :
    HomeLocalDbRepo {
    override fun dbObserveAllCurrencies() = currenciesDao.allAsObserve()
    override suspend fun allCurrenciesDb() = currenciesDao.allCurrencies()

    override suspend fun insert(currencies: Currencies) {
        externalScope.launch(Dispatchers.IO) {
            currenciesDao.insert(currencies)
        }
    }

    override suspend fun insert(rate: CurrencyRate) {
        externalScope.launch(Dispatchers.IO) {
            currencyRateDao.insert(rate)
        }
    }

    override  fun dbAllRates() = currencyRateDao.all()

    override suspend fun deleteCurrency() {
        externalScope.launch(Dispatchers.IO) {
            currenciesDao.deleteAll()
        }
    }

    override suspend fun deleteCurrencyRate() {
        externalScope.launch(Dispatchers.IO) {
            currencyRateDao.deleteAll()
        }
    }
}