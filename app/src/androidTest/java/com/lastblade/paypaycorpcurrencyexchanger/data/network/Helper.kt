package com.lastblade.paypaycorpcurrencyexchanger.data.network

import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltTestApplication
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.io.IOException
import java.io.InputStreamReader

object Helper {
    /**
     * Sets which response the [MockWebServer] should return when a request is made
     */
    fun MockWebServer.setResponse(fileName: String, responseCode: Int = 200) {
        enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(readFileFromAssets(fileName))
        )
    }

    /**
     * The  file in the [filePath] and return its content as a [String]
     */
    private fun getFileAsString(filePath: String): String {
        val inputStream = ClassLoader.getSystemResourceAsStream(filePath)
        val source = inputStream.source().buffer()

        return source.readString(Charsets.UTF_8)
    }

    private fun readFileFromAssets(fileName: String): String {
        try {
            val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as HiltTestApplication).assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}