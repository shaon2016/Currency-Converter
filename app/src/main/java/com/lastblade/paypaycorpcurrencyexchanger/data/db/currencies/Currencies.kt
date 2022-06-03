package com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currencies(
    @PrimaryKey
    var id: Long = 0,
    val currencies: HashMap<String, String>?,
)
