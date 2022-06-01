package com.lastblade.payseracurrencyexchanger.repo

import com.lastblade.payseracurrencyexchanger.data.Result

abstract class BaseRepo {
    fun errorHandler(message: ArrayList<String>, isServerError: Boolean = false): Result.Error {
        return if (isServerError) {
            Result.Error(Exception("Something went wrong! Please try again."))
        } else {
            val listString = StringBuilder()
            listString.clear()

            for (item in message.iterator()) {
                listString.append(item)
            }

            Result.Error(Exception(listString.toString()))
        }
    }
}