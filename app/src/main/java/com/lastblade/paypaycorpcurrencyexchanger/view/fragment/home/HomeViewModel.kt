package com.lastblade.paypaycorpcurrencyexchanger.view.fragment.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.paypaycorpcurrencyexchanger.data.model.CurrenciesResponse
import com.lastblade.paypaycorpcurrencyexchanger.data.model.CurrencyRateResponse
import com.lastblade.paypaycorpcurrencyexchanger.data.model.CurrencyUnitRate
import com.lastblade.paypaycorpcurrencyexchanger.repo.home.HomeRepoImpl
import com.lastblade.paypaycorpcurrencyexchanger.util.FileUtils
import com.lastblade.paypaycorpcurrencyexchanger.util.serializeToMap
import com.lastblade.paypaycorpcurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.lastblade.paypaycorpcurrencyexchanger.data.Result
import com.lastblade.paypaycorpcurrencyexchanger.util.AppConstants

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepoImpl: HomeRepoImpl,
    @ApplicationContext val context: Context,
) : BaseViewModel() {
    lateinit var selectedCurrency: String

    val currenciesAsObserve: LiveData<Currencies> get() = homeRepoImpl.dbObserveAllCurrencies()

    private val _convertedCurrencyList = MutableLiveData<List<CurrencyUnitRate>>()
    val convertedCurrencyList: LiveData<List<CurrencyUnitRate>> get() = _convertedCurrencyList

    init {
        loadCurrencies()
        loadCurrencyRate()
    }

    fun loadFakeCurrencies() {
        //        val data = getCurrencyList(context)
//
//        val map = data.serializeToMap()
//
//        viewModelScope.launch {
//            homeRepoImpl.insert(Currencies(currencies = map as HashMap<String, String>))
//        }
    }

    fun getCurrencyList(context: Context): CurrenciesResponse {
        return Gson().fromJson(FileUtils.readFileInStringFromRaw(context,
            "sample_currency_list_response.json"), CurrenciesResponse::class.java)
    }

    private fun loadCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        val currencies = homeRepoImpl.allCurrenciesDb()

        if (currencies?.currencies == null || currencies.currencies.isEmpty()) {
            fetchCurrencies()
        }
    }

    private fun fetchCurrencies() = viewModelScope.launch {
        onLoading(true)
        try {
            when (val result = homeRepoImpl.getCurrencies()) {
                is Result.Success -> {
                    val map = result.data.serializeToMap()
                    homeRepoImpl.insert(Currencies(currencies = map as HashMap<String, String>))
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

    private fun loadCurrencyRate() = viewModelScope.launch(Dispatchers.IO) {
        val currencyRate = homeRepoImpl.dbAllRates()

        if (currencyRate?.rates == null || currencyRate.rates.isEmpty()) {
            fetchCurrencyRate()
        } else {
            val serverTimestamp = currencyRate.timestamp ?: 0L
            val diff = System.currentTimeMillis() - serverTimestamp

            if (diff > AppConstants.DEVICE_CACHE_EXPIRY) {
                fetchCurrencyRate()
            }
        }
    }

    fun fakeCurrencyRate() {
        val data = getCurrencyRates(context)
        val map = data.rates.serializeToMap()

        viewModelScope.launch {
            homeRepoImpl.insert(CurrencyRate(timestamp = data.timestamp, base = data.base,
                rates = map as HashMap<String, Double>))
        }
    }

    private fun fetchCurrencyRate() = viewModelScope.launch {
        onLoading(true)

        try {
            when (val result = homeRepoImpl.getCurrencyRate()) {
                is Result.Success -> {
                    val map = result.data.rates.serializeToMap()

                    homeRepoImpl.insert(CurrencyRate(timestamp = System.currentTimeMillis(),
                        base = result.data.base,
                        rates = map as HashMap<String, Double>))
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

    fun getCurrencyRates(context: Context): CurrencyRateResponse {
        return Gson().fromJson(FileUtils.readFileInStringFromRaw(context,
            "sample_currency_rates_response.json"), CurrencyRateResponse::class.java)
    }

    fun convertUnitRate() = viewModelScope.launch(Dispatchers.IO) {
        val toConvertedRates = mutableListOf<CurrencyUnitRate>()

        val currencyRates = homeRepoImpl.dbAllRates()

        currencyRates?.rates?.keys?.forEach { toCurrency ->
            val rateTo = currencyRates.rates[toCurrency] ?: 0.0
            val rateFrom = currencyRates.rates[selectedCurrency] ?: 0.0
            val convertedRate = rateTo / rateFrom

            toConvertedRates.add(CurrencyUnitRate(toCurrency = toCurrency,
                unitRate = convertedRate))
        }

        _convertedCurrencyList.postValue(toConvertedRates)
    }
}