package com.lastblade.payseracurrencyexchanger.view.fragment.home

import com.lastblade.payseracurrencyexchanger.repo.home.HomeRepo
import com.lastblade.payseracurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepo) : BaseViewModel() {


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