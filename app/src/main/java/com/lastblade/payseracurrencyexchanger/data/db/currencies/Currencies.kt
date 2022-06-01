package com.lastblade.payseracurrencyexchanger.data.db.currencies

import androidx.room.Entity

@Entity
data class Currencies(val currencies: HashMap<String, String>)
