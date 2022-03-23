package com.lastblade.payseracurrencyexchanger.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response


class ApiHelper(val apiService: IApiService) {
    companion object {
        const val CALL_TYPE_GET = "get"
        const val CALL_TYPE_POST = "post"
        const val CALL_TYPE_PAGING = "paging"

        //endpoint
        const val ENDPOINT_LOGIN = "latest"
    }

    suspend fun <T> getApiCallObservable(
        callType: String,
        path: String,
        hashMap: HashMap<String, T>
    ): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            when (callType) {
                CALL_TYPE_GET -> {
                    apiService.getRequest(path, hashMap as HashMap<String, String>)
                }
                CALL_TYPE_POST -> {
                    apiService.postRequest(path, hashMap as HashMap<String, String>)
                }
                CALL_TYPE_PAGING -> {
                    apiService.pagingPostRequest(path, hashMap as HashMap<String, String>)
                }
                else -> {
                    apiService.sendDocuments(path, hashMap as HashMap<String, RequestBody>)
                }
            }
        }
    }
}