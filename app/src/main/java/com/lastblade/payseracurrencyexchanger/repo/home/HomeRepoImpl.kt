package com.lastblade.payseracurrencyexchanger.repo.home

import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.payseracurrencyexchanger.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val homeLocalDbRepo: HomeLocalDbRepo,
    private val homeRemoteRepo: HomeRemoteRepo
) : HomeRepo {
    override suspend fun dbAllCurrencies() = homeLocalDbRepo.dbAllCurrencies()

    override suspend fun insert(currencies: Currencies) {
        homeLocalDbRepo.insert(currencies)
    }

    override suspend fun insert(rate: CurrencyRate) {
        homeLocalDbRepo.insert(rate)
    }

    override suspend fun dbAllRates() = homeLocalDbRepo.dbAllRates()

    override suspend fun getCurrencies() = homeRemoteRepo.getCurrencies()

    override suspend fun getCurrencyRate() = homeRemoteRepo.getCurrencyRate()
}