package com.maks.services

import com.maks.api.BookingException
import com.maks.models.Phone
import com.maks.services.technology.PhoneTechnologyService
import java.util.concurrent.locks.ReentrantLock
import kotlin.streams.toList

object PhoneService {

    private val phones = listOf(
        "Samsung Galaxy S9",
        "Samsung Galaxy S8",
        "Samsung Galaxy S7",
        "Motorola Nexus 6",
        "LG Nexus 5X",
        "Huawei Honor 7X",
        "Apple iPhone X",
        "Apple iPhone 8",
        "Apple iPhone 4s",
        "Nokia 3310"
    )

    private val phoneDao = PhoneDao
    private val techService = PhoneTechnologyService

    /**
     * Parallel streams are not efficient on small datasets but since we rely on http response
     * this approach speedup data fetch
     */
    fun getAllPhones(): List<Phone> {
        return phones.parallelStream()
            .map {
                val bookingStatus = phoneDao.getBooking(it)
                val isAvailable = bookingStatus == null
                val techData = techService.fetchTechData(it)
                Phone(it, techData, isAvailable, bookingStatus)
            }.toList()
    }

    fun getPhone(phoneName: String): Phone? {
        return phones.filter { name ->
            name == phoneName
        }.map {
            val bookingStatus = phoneDao.getBooking(it)
            val isAvailable = bookingStatus == null
            val techData = techService.fetchTechData(it)
            Phone(it, techData, isAvailable, bookingStatus)
        }.getOrNull(0)
    }

    fun bookPhone(phoneName: String, userName: String): Phone? {
        phoneDao.bookPhone(phoneName, userName)
        return getPhone(phoneName)
    }

    fun returnPhone(phoneName: String, userName: String): Phone? {
        val bookingStatus = phoneDao.getBooking(phoneName)
            ?: throw BookingException("Phone $phoneName is not booked")

        if (bookingStatus.bookedBy != userName) {
            throw BookingException("Phone $phoneName is booked by other user")
        }

        phoneDao.returnPhone(phoneName)
        
        return getPhone(phoneName)
    }
}
