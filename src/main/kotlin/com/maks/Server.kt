package com.maks

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.maks.api.ApiResponse
import com.maks.api.BookingException
import com.maks.api.PhoneController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson


fun main() {
    val app = createServer().start(8080)

    app.routes {
        path("api/phones") {
            get("/") { PhoneController.fetchAllPhones.handle(it) }
            path(":phone-name") {
                get("/") { PhoneController.fetchPhone.handle(it) }
                post("book") { PhoneController.bookPhone.handle(it) }
                post("return") { PhoneController.returnPhone.handle(it) }
            }
        }
    }
}

private fun createServer() = Javalin.create { config ->
    config.enableDevLogging()
}.apply {
    exception(BookingException::class.java) { e, ctx ->
        ctx.status(400)
        ctx.json(ApiResponse(errors = listOf(e.toString())))
    }
    exception(Exception::class.java) { e, ctx ->
        e.printStackTrace()
        ctx.status(500)
        ctx.json(ApiResponse(errors = listOf("Unhandled error", e.toString())))
    }
    error(404) { ctx -> ctx.json(ApiResponse(errors = listOf("Url not found"))) }
}.events { event ->
    event.serverStarting {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        mapper.registerModule(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        JavalinJackson.configure(mapper)
    }
}
