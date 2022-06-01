package com.lastblade.payseracurrencyexchanger.repo.home

import androidx.lifecycle.LiveData
import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRate

interface HomeLocalDbRepo {
    suspend fun dbAllCurrencies(): LiveData<List<Currencies>>
    suspend fun insert(currencies: Currencies)

    suspend fun dbAllRates(): LiveData<List<CurrencyRate>>
    suspend fun insert(rate: CurrencyRate)
}