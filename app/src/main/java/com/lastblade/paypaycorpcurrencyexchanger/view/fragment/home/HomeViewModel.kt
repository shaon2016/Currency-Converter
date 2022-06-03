package com.lastblade.paypaycorpcurrencyexchanger.view.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lastblade.paypaycorpcurrencyexchanger.data.Result
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.paypaycorpcurrencyexchanger.data.model.CurrencyUnitRate
import com.lastblade.paypaycorpcurrencyexchanger.repo.home.HomeRepoImpl
import com.lastblade.paypaycorpcurrencyexchanger.util.AppConstants
import com.lastblade.paypaycorpcurrencyexchanger.util.serializeToMap
import com.lastblade.paypaycorpcurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepoImpl: HomeRepoImpl) :
    BaseViewModel() {
    /**
     * Holds the selected currency to update converted rate
     * */
    lateinit var selectedCurrency: String

    val currenciesAsObserve: LiveData<Currencies> get() = homeRepoImpl.dbObserveAllCurrencies()

    private val _convertedCurrencyList = MutableLiveData<List<CurrencyUnitRate>>()
    val convertedCurrencyList: LiveData<List<CurrencyUnitRate>> get() = _convertedCurrencyList

    init {
        loadData()
    }

    /**
     * It load the currency rate to list and currencies to spinner
     * if database is empty then it will fetch from server
     * It also checks 30 minute time passed from the last data fetch or not
     * So, We also save the fetch timestamp to database for future compare
     * */
    private fun loadData() = viewModelScope.launch(Dispatchers.IO) {
        val currencies = homeRepoImpl.allCurrenciesDb()
        val currencyRate = homeRepoImpl.dbAllRates()

        if ((currencies?.currencies == null || currencies.currencies.isEmpty())
            && (currencyRate?.rates == null || currencyRate.rates.isEmpty())
        ) {
            fetchCurrencies()
            fetchCurrencyRate()
        } else {
            val serverTimestamp = currencyRate.timestamp ?: 0L
            val diff = System.currentTimeMillis() - serverTimestamp

            if (diff > AppConstants.DEVICE_CACHE_EXPIRY) {
                homeRepoImpl.deleteCurrency()
                homeRepoImpl.deleteCurrencyRate()

                fetchCurrencies()
                fetchCurrencyRate()
            }
        }
    }

    /**
     * Fetch currencies from server
     * */
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

    /**
     * Fetch the currency rate from server
     * */
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

    /**
     * Convert exchange rate
     * Based on a currency selection it convert rest of the currency
     * */
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