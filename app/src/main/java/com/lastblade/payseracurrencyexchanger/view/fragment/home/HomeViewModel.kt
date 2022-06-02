package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lastblade.payseracurrencyexchanger.data.Result
import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.data.model.CurrenciesResponse
import com.lastblade.payseracurrencyexchanger.repo.home.HomeRepoImpl
import com.lastblade.payseracurrencyexchanger.util.FileUtils
import com.lastblade.payseracurrencyexchanger.util.serializeToMap
import com.lastblade.payseracurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepoImpl: HomeRepoImpl,
    @ApplicationContext val context: Context,
) : BaseViewModel() {

    val currencies: LiveData<Currencies> get() = homeRepoImpl.dbAllCurrencies()

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