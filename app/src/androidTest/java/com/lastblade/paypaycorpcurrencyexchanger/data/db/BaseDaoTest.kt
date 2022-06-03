package com.lastblade.paypaycorpcurrencyexchanger.data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var context: Context
    private lateinit var db: RoomDB
//    private lateinit var dao: CartDao

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, RoomDB::class.java)
            .allowMainThreadQueries()
            .build()
//        dao = db.cartDao()
    }

    @After
    fun teardown() {
        db.close()
    }


}