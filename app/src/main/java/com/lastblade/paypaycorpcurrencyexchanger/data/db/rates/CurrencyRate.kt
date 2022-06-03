package com.lastblade.paypaycorpcurrencyexchanger.data.db.rates

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRate(
    @PrimaryKey
    var id: Long = 0,
    val base: String = "USD",
    var timestamp: Long?,
    val rates: HashMap<String, Double>?,
)
