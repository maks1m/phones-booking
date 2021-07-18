package com.maks.services.technology

import com.maks.models.tech.TechPhoneData
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PhoneTechnologyServiceTest {

    @Test
    fun `should return tech data when tech api has response`() {
        mockkObject(PhoneTechnologyApi)
        every {
            PhoneTechnologyApi.fetchTechData(any())
        } returns TechPhoneData(
            objectId = "mockID",
            twoG = "mock 2 G",
            threeG = "mock 3 G",
            fourG = "mock 4 G"
        )

        val techData = PhoneTechnologyService.fetchTechData("mock phone name")
        assertNotNull(techData?.twoG, "2 G is empty!")
        assertNotNull(techData?.threeG, "3 G is empty!")
        assertNotNull(techData?.fourG, "4 G is empty!")
    }

}
