package com.lastblade.payseracurrencyexchanger.repo.home

import com.lastblade.payseracurrencyexchanger.data.Result
import com.lastblade.payseracurrencyexchanger.data.model.CurrenciesResponse
import com.lastblade.payseracurrencyexchanger.data.model.CurrencyRateResponse

interface HomeRemoteRepo {
    suspend fun getCurrencies() : Result<CurrenciesResponse>
    suspend fun getCurrencyRate() : Result<CurrencyRateResponse>
}