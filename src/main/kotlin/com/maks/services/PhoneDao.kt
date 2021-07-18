package com.maks.services

import com.maks.api.BookingException
import com.maks.models.BookingStatus
import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock

object PhoneDao {

    private val lock = ReentrantLock()

    private val logger = KotlinLogging.logger {}

    private val phonesWithStatus = ConcurrentHashMap<String, BookingStatus?>()

    fun getBooking(phoneName: String): BookingStatus? {
        return phonesWithStatus[phoneName]
    }

    fun bookPhone(phoneName: String, userName: String) {
        try {
            lock.lock()
            if (phonesWithStatus.containsKey(phoneName)) {
                throw BookingException("$phoneName is already booked")
            }
            phonesWithStatus.compute(phoneName) { k, v ->
                BookingStatus(userName, LocalDateTime.now())
            }
            logger.info { "$phoneName is booked by $userName" }
        } finally {
            lock.unlock()
        }
    }

    fun returnPhone(phoneName: String) {
        phonesWithStatus.remove(phoneName)
    }

}

