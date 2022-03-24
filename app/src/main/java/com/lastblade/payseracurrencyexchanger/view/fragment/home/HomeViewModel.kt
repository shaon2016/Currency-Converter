package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lastblade.payseracurrencyexchanger.repo.HomeRepo
import com.lastblade.payseracurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepo) : BaseViewModel() {
    val myBalance = MutableLiveData(1000.00)
    val selectedCurrencyValue = MutableLiveData(0.0)
    val calculatedValue = MutableLiveData(0.0)
    var calculationDone = 0

    private var ratesObj = JSONObject()

    private val timer: Timer = Timer()
    private val timerTask: TimerTask

    init {
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

                    Log.d("DATATAG", ratesObj.toString())
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
        if (convertFromCurrencyValue.isNotEmpty()) {
            selectedCurrencyValue.value =
                ratesObj.get(selectedConvertToCurrency).toString().toDouble() // based on Euro

            calculationDone++
            val convertedValue =
                selectedCurrencyValue.value!!.toDouble() * convertFromCurrencyValue.toDouble()

            if (calculationDone > 5) {
                calculatedValue.value = convertedValue * .7
            } else {
                calculatedValue.value = convertedValue
            }

            myBalance.value = myBalance.value!! - calculatedValue.value!!
        }
    }
}