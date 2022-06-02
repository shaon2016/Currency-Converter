package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.payseracurrencyexchanger.data.model.CurrenciesResponse
import com.lastblade.payseracurrencyexchanger.data.model.CurrencyRateResponse
import com.lastblade.payseracurrencyexchanger.data.model.CurrencyUnitRate
import com.lastblade.payseracurrencyexchanger.repo.home.HomeRepoImpl
import com.lastblade.payseracurrencyexchanger.util.FileUtils
import com.lastblade.payseracurrencyexchanger.util.serializeToMap
import com.lastblade.payseracurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepoImpl: HomeRepoImpl,
    @ApplicationContext val context: Context,
) : BaseViewModel() {
    lateinit var selectedCurrency: String

    val currencies: LiveData<Currencies> get() = homeRepoImpl.dbAllCurrencies()

    private val _convertedCurrencyList = MutableLiveData<List<CurrencyUnitRate>>()
    val convertedCurrencyList: LiveData<List<CurrencyUnitRate>> get() = _convertedCurrencyList

    fun loadCurrencies() {
//        viewModelScope.launch {
//            onLoading(true)
//            try {
//                when (val result = homeRepoImpl.getCurrencies()) {
//                    is Result.Success -> {
//                        val jo = JSONObject(result.data.toString())
//
//                        val hashMap = HashMap<String, String>()
//                        jo.keys().forEach {
//                            hashMap[it] = jo.getString(it)
//                        }
//
//                        homeRepoImpl.insert(Currencies(currencies = hashMap))
//                    }
//                    is Result.Error -> {
//                        errorMessage.value = result.exception.message
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//            onLoading(false)
//        }

        val data = getCurrencyList(context)

        val map = data.serializeToMap()

        viewModelScope.launch {
            homeRepoImpl.insert(Currencies(currencies = map as HashMap<String, String>))
        }

    }

    fun getCurrencyList(context: Context): CurrenciesResponse {
        return Gson().fromJson(FileUtils.readFileInStringFromRaw(context,
            "sample_currency_list_response"), CurrenciesResponse::class.java)

    }

    fun loadCurrencyRate() {

    }

    fun fetchCurrencyRate() {
        val data = getCurrencyRates(context)
        val map = data.rates.serializeToMap()

        viewModelScope.launch {
            homeRepoImpl.insert(CurrencyRate(timestamp = data.timestamp, base = data.base,
                rates = map as HashMap<String, Double>))
        }
    }

    fun getCurrencyRates(context: Context): CurrencyRateResponse {
        return Gson().fromJson(FileUtils.readFileInStringFromRaw(context,
            "sample_currency_rates_response"), CurrencyRateResponse::class.java)
    }

     fun convertUnitRate()  = viewModelScope.launch(Dispatchers.IO) {
         val toConvertedRates = mutableListOf<CurrencyUnitRate>()

         val currencyRates = homeRepoImpl.dbAllRates()

         currencyRates.rates?.keys?.forEach { toCurrency ->
             val rateTo = currencyRates.rates[toCurrency] ?: 0.0
             val rateFrom = currencyRates.rates[selectedCurrency] ?: 0.0
             val convertedRate = rateTo / rateFrom

             toConvertedRates.add(CurrencyUnitRate(toCurrency = toCurrency,
                 unitRate = convertedRate))
         }

         _convertedCurrencyList.postValue(toConvertedRates)
     }
}