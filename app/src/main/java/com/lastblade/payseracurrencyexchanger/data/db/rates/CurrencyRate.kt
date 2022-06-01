package com.lastblade.payseracurrencyexchanger.data.db.rates

import androidx.room.Entity

@Entity
data class CurrencyRate(val rates: HashMap<String, Double>)
