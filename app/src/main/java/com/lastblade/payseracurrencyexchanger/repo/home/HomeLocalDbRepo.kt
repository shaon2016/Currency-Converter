package com.lastblade.payseracurrencyexchanger.repo.home

import androidx.lifecycle.LiveData
import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRate

interface HomeLocalDbRepo {
    fun dbAllCurrencies(): LiveData<Currencies>
    suspend fun insert(currencies: Currencies)

    fun dbAllRates(): CurrencyRate
    suspend fun insert(rate: CurrencyRate)
}