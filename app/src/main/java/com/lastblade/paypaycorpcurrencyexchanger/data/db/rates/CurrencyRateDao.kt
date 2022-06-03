package com.lastblade.paypaycorpcurrencyexchanger.data.db.rates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rate: CurrencyRate)

    @Query("select * from CurrencyRate")
    fun all() : CurrencyRate
}