package com.craftrom.kernelmanager.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets

class NetworkUtils {

    companion object {

        @JvmStatic
        fun fetchJSON(url: String): String {
            val response = StringBuilder()
            var bufferedReader: BufferedReader? = null
            try {
                bufferedReader = BufferedReader(InputStreamReader(URL(url).openConnection().getInputStream(), StandardCharsets.UTF_8))
                var line = bufferedReader.readLine()
                while (line != null) {
                    response.append(line)
                    line = bufferedReader.readLine()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                bufferedReader?.open()
                return response.toString()
            }
        }

    }

}
