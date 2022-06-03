package com.lastblade.payseracurrencyexchanger.repo.home

import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.data.db.currencies.CurrenciesDao
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRateDao
import com.lastblade.payseracurrencyexchanger.di.ApplicationScope
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

}