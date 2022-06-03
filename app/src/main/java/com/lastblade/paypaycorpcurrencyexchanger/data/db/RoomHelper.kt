package com.lastblade.paypaycorpcurrencyexchanger.data.db

import android.content.Context
import androidx.room.Room

class RoomHelper(private val context: Context)  {

    private val db = Room.databaseBuilder(context, RoomDB::class.java, "currency_converter_db").allowMainThreadQueries().build()


    fun getDatabase(): RoomDB {
        return db
    }
}