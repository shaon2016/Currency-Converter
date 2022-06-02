package com.lastblade.payseracurrencyexchanger.repo.home

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
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
    override fun dbAllCurrencies() = currenciesDao.all()

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