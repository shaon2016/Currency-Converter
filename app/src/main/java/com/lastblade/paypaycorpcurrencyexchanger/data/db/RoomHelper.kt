package com.lastblade.paypaycorpcurrencyexchanger.data.db

import android.content.Context
import androidx.room.Room

class RoomHelper(context: Context, isForTest: Boolean = false) {

    private val db = if (isForTest)
        Room.inMemoryDatabaseBuilder(context, RoomDB::class.java)
            .allowMainThreadQueries()
            .build()
    else
        Room.databaseBuilder(context, RoomDB::class.java, "currency_converter_db")
            .allowMainThreadQueries().build()


    fun getDatabase(): RoomDB {
        return db
    }
}