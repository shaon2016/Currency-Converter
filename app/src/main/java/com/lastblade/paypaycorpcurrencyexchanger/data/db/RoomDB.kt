package com.lastblade.paypaycorpcurrencyexchanger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies.CurrenciesDao
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRate
import com.lastblade.paypaycorpcurrencyexchanger.data.db.rates.CurrencyRateDao

@Database(entities = [CurrencyRate::class, Currencies::class], version = 1, exportSchema = false)
@TypeConverters(MapTypeConverter::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun currencyRateDao() : CurrencyRateDao
    abstract fun currenciesDao(): CurrenciesDao
}