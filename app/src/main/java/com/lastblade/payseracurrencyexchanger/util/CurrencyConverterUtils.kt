package com.lastblade.payseracurrencyexchanger.util

import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRate

object CurrencyConverterUtils {
//    fun getExchangeRate(from: String, to: String, currencyRates: CurrencyRate): Double {
//        val ratesMap = currencyRates.rates
//        if (ratesMap.containsKey("$from$to") || ratesMap.containsKey("$to$from")) {
//            return if (ratesMap.containsKey("$from$to")) {
//                ratesMap["$from$to"]!!
//            } else {
//                (1 / ratesMap["$to$from"]!!)
//            }
//        } else {
//            if (ratesMap.containsKey("${currencyRates.base}$from") && ratesMap.containsKey("${currencyRates.base}$to")) {
//                val rateSourceFrom = ratesMap["${currencyRates.base}$from"]!!
//                val rateSourceTo = ratesMap["${currencyRates.base}$to"]!!
//                return rateSourceTo / rateSourceFrom
//            }
//        }
//
//        throw NoSuchFieldException("No exchange rates found for selected currency")
//    }
}
