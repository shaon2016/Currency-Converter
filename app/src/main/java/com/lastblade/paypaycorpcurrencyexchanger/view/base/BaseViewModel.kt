package com.lastblade.paypaycorpcurrencyexchanger.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lastblade.paypaycorpcurrencyexchanger.util.SingleLiveEvent
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    private var response: Response<ResponseBody>? = null

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var errorMessage = SingleLiveEvent<String>()


    fun onLoading(isLoader: Boolean) {
        _isLoading.value = isLoader
    }
}