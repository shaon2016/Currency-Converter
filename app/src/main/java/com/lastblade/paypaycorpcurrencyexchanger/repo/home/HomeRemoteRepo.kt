package com.lastblade.paypaycorpcurrencyexchanger.repo.home

import com.lastblade.paypaycorpcurrencyexchanger.data.Result
import com.lastblade.paypaycorpcurrencyexchanger.data.model.CurrenciesResponse
import com.lastblade.paypaycorpcurrencyexchanger.data.model.CurrencyRateResponse

interface HomeRemoteRepo {
    suspend fun getCurrencies() : Result<CurrenciesResponse>
    suspend fun getCurrencyRate() : Result<CurrencyRateResponse>
}