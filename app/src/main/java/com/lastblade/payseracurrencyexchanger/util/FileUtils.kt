package com.lastblade.payseracurrencyexchanger.util

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

object FileUtils {

    fun readJsonFromRaw(context: Context, fileName: String) : JSONObject {
        return JSONObject(readFileInStringFromRaw(context, fileName))
    }

    fun readFileInStringFromRaw(context: Context, fileName: String) : String? {
        val fileContent: String?
        try {
            val ins = context.resources.openRawResource(context.resources.getIdentifier(fileName,
                "raw", context.packageName))
            val size = ins.available()
            val buffer = ByteArray(size)
            ins.read(buffer)
            ins.close()
            fileContent = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return fileContent
    }

}