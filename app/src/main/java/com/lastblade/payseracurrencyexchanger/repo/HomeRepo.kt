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
        hashMap["apikey"] = "a3212fc45e784360bbc882ea2e16136a"
        return dataManager.apiHelper.getApiCallObservable(
            ApiHelper.CALL_TYPE_GET,
            ApiHelper.ENDPOINT_CURRENCY_RATE,
            hashMap
        )
    }
}