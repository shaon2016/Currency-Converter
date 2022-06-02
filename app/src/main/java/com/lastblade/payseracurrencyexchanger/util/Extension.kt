package com.lastblade.payseracurrencyexchanger.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun EditText.showKeyboard() {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.postDelayed({
        this.requestFocus()
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }, 100)
}

fun Activity.hideKeyboard() {
    val inputManager = getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    val focusedView = currentFocus

    if (focusedView != null) {
        inputManager.hideSoftInputFromWindow(
            focusedView.windowToken,
            InputMethodManager.RESULT_HIDDEN
        )
    }
}


val gson = Gson()

//convert a data class to a map
fun <T> T.serializeToMap(): HashMap<String, Any> {
    return convert()
}

//convert a map to a data class
inline fun <reified T> HashMap<String, Any>.toDataClass(): T {
    return convert()
}

//convert an object of type I to type O
inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}