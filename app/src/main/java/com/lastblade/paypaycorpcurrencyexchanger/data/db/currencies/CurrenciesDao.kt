package com.lastblade.paypaycorpcurrencyexchanger.data.db.currencies

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencies: Currencies)

    @Query("select * from Currencies")
    fun allAsObserve() : LiveData<Currencies>

    @Query("select * from Currencies")
    fun allCurrencies() : Currencies

    @Query("delete from currencies")
    fun deleteAll()
}