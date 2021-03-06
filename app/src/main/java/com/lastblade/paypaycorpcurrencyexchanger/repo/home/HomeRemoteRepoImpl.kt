package com.lastblade.paypaycorpcurrencyexchanger.repo.home

import com.google.gson.Gson
import com.lastblade.paypaycorpcurrencyexchanger.data.Result
import com.lastblade.paypaycorpcurrencyexchanger.data.model.CurrenciesResponse
import com.lastblade.paypaycorpcurrencyexchanger.data.model.CurrencyRateResponse
import com.lastblade.paypaycorpcurrencyexchanger.data.network.ApiEndPoint
import com.lastblade.paypaycorpcurrencyexchanger.data.network.ApiHelper
import com.lastblade.paypaycorpcurrencyexchanger.repo.BaseRepo
import com.lastblade.paypaycorpcurrencyexchanger.util.AppConstants
import javax.inject.Inject

class HomeRemoteRepoImpl @Inject constructor(private val apiHelper : ApiHelper) : BaseRepo(), HomeRemoteRepo {
    override suspend fun getCurrencies(): Result<CurrenciesResponse> {
        val map = HashMap<String, String>()

        return try {
            val result =
                apiHelper.getApiCallObservable(ApiHelper.CALL_TYPE_GET, ApiEndPoint.CURRENCIES, map)

            if (result.isSuccessful) {
                val response =
                    Gson().fromJson(result.body()?.string(), CurrenciesResponse::class.java)

                Result.Success(response)
            } else
                errorHandler(arrayListOf("Something went wrong"))
        } catch (e: Exception) {
            e.printStackTrace()
            errorHandler(arrayListOf("Something went wrong"), isServerError = true)
        }
    }

    override suspend fun getCurrencyRate(): Result<CurrencyRateResponse> {
        val map = HashMap<String, String>()
        map["app_id"] = AppConstants.APP_ID

        return try {
            val result =
                apiHelper.getApiCallObservable(ApiHelper.CALL_TYPE_GET, ApiEndPoint.CURRENCY_RATE, map)

            if (result.isSuccessful) {
                val response =
                    Gson().fromJson(result.body()?.string(), CurrencyRateResponse::class.java)

                Result.Success(response)
            } else
                errorHandler(arrayListOf("Something went wrong"))
        } catch (e: Exception) {
            e.printStackTrace()
            errorHandler(arrayListOf("Something went wrong"), isServerError = true)
        }
    }
}