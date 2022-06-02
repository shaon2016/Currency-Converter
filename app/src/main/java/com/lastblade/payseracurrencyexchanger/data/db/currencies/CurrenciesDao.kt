package com.lastblade.payseracurrencyexchanger.data.db.currencies

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencies: Currencies)

    @Query("select * from Currencies")
    fun all() : LiveData<Currencies>
}