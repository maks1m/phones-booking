package com.maks.api

import com.maks.services.PhoneService
import io.javalin.http.Handler
import mu.KotlinLogging

object PhoneController {

    private const val PHONE_NAME_PARAM = "phone-name"

    private val logger = KotlinLogging.logger {}
    private val phoneService = PhoneService

    val fetchAllPhones = Handler { ctx ->
        logger.debug { "Returning all phones..." }
        val phones = phoneService.getAllPhones()
        ctx.json(ApiResponse(phones))
    }

    val fetchPhone = Handler { ctx ->
        val phoneName = ctx.pathParam(PHONE_NAME_PARAM)
        val phone = phoneService.getPhone(phoneName)
        ctx.json(ApiResponse(phone))
    }

    val bookPhone = Handler { ctx ->
        val phoneName = ctx.pathParam(PHONE_NAME_PARAM)
        val userName = ctx.header("userName")
            ?: throw BookingException("'userName' header it not set!")

        val newPhoneStatus = phoneService.bookPhone(phoneName, userName)

        ctx.json(ApiResponse(newPhoneStatus))
    }

    val returnPhone = Handler { ctx ->
        val phoneName = ctx.pathParam(PHONE_NAME_PARAM)
        val userName = ctx.header("userName")
            ?: throw BookingException("'userName' header it not set!")

        val newPhoneStatus = phoneService.returnPhone(phoneName, userName)

        ctx.json(ApiResponse(newPhoneStatus))
    }

}
