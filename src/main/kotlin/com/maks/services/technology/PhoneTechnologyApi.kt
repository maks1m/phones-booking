package com.maks.services.technology

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.maks.models.tech.TechApiResponse
import com.maks.models.tech.TechPhoneData
import mu.KotlinLogging
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object PhoneTechnologyApi {

    private const val API_HOST_URL = "https://parseapi.back4app.com/classes/Cellphonedataset_Dataset_Cell_Phones_Model_Brand"
    private const val APPLICATION_ID = "PsFCMCmib9mH76ccwyCeJd9jysE625khn1Y4IiZM"
    private const val REST_API_KEY = "1uHDSm8Qfq0G2BIUpWAR9nlqgXMEPgpKBOO6iAzH"

    private val logger = KotlinLogging.logger {}

    fun fetchTechData(phoneName: String): TechPhoneData? {
        val url = urlForPhone(phoneName)
        val urlConnection = createConnection(url)
        var result: TechPhoneData? = null
        try {
            logger.info { "Requesting data: ${urlConnection.url}" }
            val responseStr = urlConnection.inputStream.bufferedReader().use { it.readText() }
            val mapper = jacksonObjectMapper()
            val response = mapper.readValue(responseStr, TechApiResponse::class.java)
            logger.info { response }
            result = response.results?.get(0)
        } catch (e: Exception) {
            logger.error { e.toString() }
        } finally {
            urlConnection.disconnect()
        }
        return result
    }

    private fun createConnection(url: URL): HttpURLConnection {
        val urlConnection = url.openConnection() as HttpURLConnection
        applyHeaders(urlConnection)
        return urlConnection
    }

    private fun applyHeaders(urlConnection: HttpURLConnection) {
        urlConnection.setRequestProperty("X-Parse-Application-Id", APPLICATION_ID)
        urlConnection.setRequestProperty("X-Parse-REST-API-Key", REST_API_KEY)
    }

    private fun urlForPhone(phoneName: String): URL {
        val (phoneBrand, phoneModel) = parsePhoneBrandModel(phoneName)
        val whereConditionStr = """{"Brand": "$phoneBrand", "Model": {"${"$"}regex": "$phoneModel${"$"}"}}"""
        val whereCondition = URLEncoder.encode(whereConditionStr, "utf-8")

        return URL("$API_HOST_URL?limit=1&where=${whereCondition}")
    }

    private fun parsePhoneBrandModel(phoneName: String): Pair<String, String> {
        val brandName = phoneName.substringBefore(" ", "")
        val modelName = phoneName.substringAfter(" ", "")
        return Pair(brandName, modelName)
    }
}


