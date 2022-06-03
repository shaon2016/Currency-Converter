package com.lastblade.paypaycorpcurrencyexchanger.repo.home

import androidx.lifecycle.LiveData
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRate

interface HomeLocalDbRepo {
    fun dbObserveAllCurrencies(): LiveData<Currencies>
    suspend fun allCurrenciesDb(): Currencies
    suspend fun insert(currencies: Currencies)

    fun dbAllRates(): CurrencyRate
    suspend fun insert(rate: CurrencyRate)
}