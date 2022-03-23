package com.lastblade.payseracurrencyexchanger.view.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lastblade.payseracurrencyexchanger.data.model.CurrencyRateResponse
import com.lastblade.payseracurrencyexchanger.repo.HomeRepo
import com.lastblade.payseracurrencyexchanger.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepo) : BaseViewModel() {

    private val _currencyRateResponse = MutableLiveData<CurrencyRateResponse>()
    val currencyRateResponse: LiveData<CurrencyRateResponse> = _currencyRateResponse

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
                _currencyRateResponse.value = Gson().fromJson(
                    response.body()?.string(),
                    CurrencyRateResponse::class.java
                )
            }
        }
    }

    override fun onCleared() {
        timer.cancel()
        super.onCleared()
    }
}