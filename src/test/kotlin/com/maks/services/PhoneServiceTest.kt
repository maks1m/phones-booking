package com.maks.services

import com.maks.models.tech.TechPhoneData
import com.maks.services.technology.PhoneTechnologyApi
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PhoneServiceTest {

    @BeforeEach
    internal fun setUp() {
        mockkObject(PhoneTechnologyApi)
        every {
            PhoneTechnologyApi.fetchTechData(any())
        } returns TechPhoneData(
            objectId = "mockID",
            twoG = "mock 2 G",
            threeG = "mock 3 G",
            fourG = "mock 4 G"
        )
    }

    @ObsoleteCoroutinesApi
    @Test
    fun `each user should book and return phone desired number of times `() {
        val phoneName = "Nokia 3310"

        runBlocking {
            val scope = CoroutineScope(
                newFixedThreadPoolContext(20, "synchronizationPool")
            )
            scope.launch {
                val coroutines = 1.rangeTo(1000).map {
                    val userName = "user$it"
                    launch {
                        for (i in 1..20) {
                            while (PhoneService.getPhone(phoneName)?.isAvailable != true) {

                            }
                            try {
                                PhoneService.bookPhone(phoneName, userName)
                                PhoneService.returnPhone(phoneName, userName)
                            } finally {
                                
                            }
                        }
                    }
                }

                coroutines.forEach { coroutine ->
                    coroutine.join()
                }
            }.join()
        }

        PhoneService.getAllPhones().forEach {
            assertTrue(it.isAvailable)
        }
    }

}
