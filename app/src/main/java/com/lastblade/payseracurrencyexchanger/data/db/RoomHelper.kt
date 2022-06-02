package com.lastblade.payseracurrencyexchanger.data.db

import android.content.Context
import androidx.room.Room

class RoomHelper(private val context: Context)  {

    private val db = Room.databaseBuilder(context, RoomDB::class.java, "currency_converter_db").build()


    fun getDatabase(): RoomDB {
        return db
    }
}