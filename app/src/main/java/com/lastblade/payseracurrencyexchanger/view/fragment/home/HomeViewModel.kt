package com.lastblade.payseracurrencyexchanger.view.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lastblade.payseracurrencyexchanger.data.model.CalculatedValueModel
import com.lastblade.payseracurrencyexchanger.data.model.MyBalanceModel
import com.lastblade.payseracurrencyexchanger.repo.home.HomeRepo
import com.lastblade.payseracurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepo) : BaseViewModel() {
    val myBalances = MutableLiveData<ArrayList<MyBalanceModel>>()
    var calculatedValueModel = MutableLiveData<CalculatedValueModel?>(null)
    var currencyCalculationDone = 0
    private val commissionFee = .70

    private var ratesObj = JSONObject()

    private val timer: Timer = Timer()
    private val timerTask: TimerTask

    init {
        val initBalance = MyBalanceModel("USD", 1000.00)
        val newList = ArrayList<MyBalanceModel>()
        newList.add(initBalance)
        myBalances.value = newList

        timerTask = object : TimerTask() {
            override fun run() {
                callExchangeRateApi()
            }
        }
        timer.scheduleAtFixedRate(timerTask, 0, 5000)
    }


    private fun callExchangeRateApi() {
        viewModelScope.launch {
            val response = repo.fetchExchangeRateAPI()

            if (response.isSuccessful) {
                try {
                    val jo = JSONObject(response.body()?.string())
                    ratesObj = jo.getJSONObject("rates")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCleared() {
        timer.cancel()
        super.onCleared()
    }

    /**
     * @param convertFromCurrencyValue usd value
     * @param selectedConvertToCurrency converted to currency string
     * */
    fun calculateCurrency(convertFromCurrencyValue: String, selectedConvertToCurrency: String) {
        try {
            if (convertFromCurrencyValue.isNotEmpty()) {
                val selectedCurrencyToValue =
                    ratesObj.get(selectedConvertToCurrency).toString().toDouble() // based on USD

                currencyCalculationDone++
                val convertedValue = convertFromCurrencyValue.toDouble() * selectedCurrencyToValue

                if (currencyCalculationDone > 5) {
                    calculatedValueModel.value = CalculatedValueModel(
                        convertFromCurrencyValue.toDouble(),
                        convertedValue, commissionFee
                    )

                    // Updating my usd balance
                    myBalances.value?.get(0)!!.value =
                        myBalances.value?.get(0)!!.value - convertFromCurrencyValue.toDouble() - commissionFee
                } else {
                    calculatedValueModel.value = CalculatedValueModel(
                        convertFromCurrencyValue.toDouble(),
                        convertedValue, null
                    )

                    // Updating my usd balance
                    myBalances.value?.get(0)!!.value =
                        myBalances.value?.get(0)!!.value - convertFromCurrencyValue.toDouble()
                }

                constructMyBalance(MyBalanceModel(selectedConvertToCurrency, convertedValue))
                myBalances.notify()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun constructMyBalance(balance: MyBalanceModel) {
        val foundBalance = myBalances.value!!.find { it.currencyStr == balance.currencyStr }
        if (foundBalance != null) {
            foundBalance.value = balance.value
            val index = myBalances.value!!.indexOf(foundBalance)
            myBalances.value!![index] = foundBalance
            myBalances.notify()
        } else {
            val oldList = myBalances.value!!
            val newList = ArrayList<MyBalanceModel>()
            newList.add(balance)
            oldList.addAll(newList)
            myBalances.value = oldList
        }
    }
}