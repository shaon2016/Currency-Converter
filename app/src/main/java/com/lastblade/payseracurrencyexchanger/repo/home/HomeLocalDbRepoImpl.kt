package com.lastblade.payseracurrencyexchanger.repo.home

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.data.db.currencies.CurrenciesDao
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRateDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeLocalDbRepoImpl @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val currencyRateDao: CurrencyRateDao
) :
    HomeLocalDbRepo {
    override suspend fun dbAllCurrencies() = currenciesDao.all()

    override suspend fun insert(currencies: Currencies) {
        currenciesDao.insert(currencies)
    }

    override suspend fun insert(rate: CurrencyRate) {
        currencyRateDao.insert(rate)
    }

    override suspend fun dbAllRates() = currencyRateDao.all()

}