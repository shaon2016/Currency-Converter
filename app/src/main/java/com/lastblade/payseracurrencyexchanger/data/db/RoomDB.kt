package com.lastblade.payseracurrencyexchanger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyRate::class, Currencies::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun currencyRateDao: CurrencyRateDao
    abstract fun currenciesDao: CurrenciesDao
}