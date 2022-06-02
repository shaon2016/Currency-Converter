package com.lastblade.payseracurrencyexchanger.data.db.currencies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currencies(
    @PrimaryKey
    var id: Long,
    val currencies: HashMap<String, String>?,
)
