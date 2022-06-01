package com.lastblade.payseracurrencyexchanger.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.HashMap

class MapTypeConverter {

    @TypeConverter
    fun stringToStringMap(value: String): HashMap<String, String> {
        return Gson().fromJson(value,  object : TypeToken<HashMap<String, String>>() {}.type)
    }

    @TypeConverter
    fun stringMapToString(value: HashMap<String, String>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }


    @TypeConverter
    fun stringToDoubleMap(value: String): HashMap<String, Double> {
        return Gson().fromJson(value,  object : TypeToken<HashMap<String, Double>>() {}.type)
    }

    @TypeConverter
    fun doubleMapToString(value: HashMap<String, Double>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }

}