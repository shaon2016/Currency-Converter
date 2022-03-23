package com.lastblade.payseracurrencyexchanger.repo

import com.lastblade.payseracurrencyexchanger.data.DataManager
import com.lastblade.payseracurrencyexchanger.data.network.ApiHelper
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class HomeRepo @Inject constructor(private val dataManager: DataManager) {
    suspend fun fetchExchangeRateAPI(
    ): Response<ResponseBody> {
        val hashMap = HashMap<String, String>()
        hashMap["access_key"] = "f52dd4e8f675947f568749314c1c4712"
        return dataManager.apiHelper.getApiCallObservable(
            ApiHelper.CALL_TYPE_GET,
            ApiHelper.ENDPOINT_CURRENCY_RATE,
            hashMap
        )
    }
}