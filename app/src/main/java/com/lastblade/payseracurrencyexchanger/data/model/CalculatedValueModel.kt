package com.lastblade.payseracurrencyexchanger.data.model

data class CalculatedValueModel(
    val convertedFromValue: Double,
    val convertedToValue: Double,
    val commissionFee: Double? = null
)
