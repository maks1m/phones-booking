package com.maks.services.technology

import com.maks.models.PhoneTechnology
import java.util.concurrent.ConcurrentHashMap

object PhoneTechnologyService {

    private val api = PhoneTechnologyApi

    private val cachePhoneWithTechData = ConcurrentHashMap<String, PhoneTechnology?>()

    fun fetchTechData(phoneName: String): PhoneTechnology? {
        return cachePhoneWithTechData.getOrPut(phoneName) {
            val techData = api.fetchTechData(phoneName)
            with(techData) {
                PhoneTechnology(this?.twoG, this?.threeG, this?.fourG)
            }
        }
    }

}
