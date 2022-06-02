package com.lastblade.payseracurrencyexchanger.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.lastblade.payseracurrencyexchanger.util.SingleLiveEvent
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.reflect.KClass

abstract class BaseViewModel : ViewModel() {
    private var response: Response<ResponseBody>? = null

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var errorMessage = SingleLiveEvent<String>()


    fun onLoading(isLoader: Boolean) {
        _isLoading.value = isLoader
    }
}