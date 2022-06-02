package com.lastblade.payseracurrencyexchanger.data.db.rates

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRate(
    @PrimaryKey
    var id: Long,
    val base: String,
    val rates: HashMap<String, Double>?,
)
