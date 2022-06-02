package com.lastblade.payseracurrencyexchanger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.data.db.currencies.CurrenciesDao
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.payseracurrencyexchanger.data.db.rates.CurrencyRateDao

@Database(entities = [CurrencyRate::class, Currencies::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun currencyRateDao() : CurrencyRateDao
    abstract fun currenciesDao(): CurrenciesDao
}