package com.lastblade.paypaycorpcurrencyexchanger.repo.home

import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRate
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val homeLocalDbRepo: HomeLocalDbRepo,
    private val homeRemoteRepo: HomeRemoteRepo,
) : HomeRepo {
    override fun dbObserveAllCurrencies() = homeLocalDbRepo.dbObserveAllCurrencies()
    override suspend fun allCurrenciesDb() = homeLocalDbRepo.allCurrenciesDb()

    override suspend fun insert(currencies: Currencies) {
        homeLocalDbRepo.insert(currencies)
    }

    override suspend fun insert(rate: CurrencyRate) {
        homeLocalDbRepo.insert(rate)
    }

    override  fun dbAllRates() = homeLocalDbRepo.dbAllRates()

    override suspend fun getCurrencies() = homeRemoteRepo.getCurrencies()

    override suspend fun getCurrencyRate() = homeRemoteRepo.getCurrencyRate()
}