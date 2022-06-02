package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lastblade.payseracurrencyexchanger.data.Result
import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.repo.home.HomeRepo
import com.lastblade.payseracurrencyexchanger.repo.home.HomeRepoImpl
import com.lastblade.payseracurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepoImpl: HomeRepoImpl) : BaseViewModel() {

    fun loadCurrencies() {
        viewModelScope.launch {
            onLoading(true)
            try {
                when(val result = homeRepoImpl.getCurrencies()) {
                    is Result.Success -> {

                    }
                    is Result.Error -> {
                        errorMessage.value = result.exception.message
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            onLoading(false)
        }
    }

    /**
     * This function is responsible for converting unit currency rate to
     * all available currencies in DB
     */
    fun convertToUnitCurrencyRate(fromCurrency: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val toCurrencyList: ArrayList<String> = ArrayList()
//            val unitRateList: ArrayList<CurrencyUnitRate> = ArrayList()
//
//            val currencyList = interactor?.loadCurrencyList()
//            val currencyRates = interactor?.loadCurrencyRates()
//            currencyList?.currencyList?.keys?.toCollection(toCurrencyList)
//            for (toCurrency in toCurrencyList) {
//                if (toCurrency == fromCurrency) continue
//                unitRateList.add(CurrencyUnitRate(toCurrency,
//                    CurrencyConverterUtils.getExchangeRate(fromCurrency,
//                        toCurrency,
//                        currencyRates!!)))
//            }
////            getView()?.updateExchangeRates(unitRateList)
//        }
    }
}