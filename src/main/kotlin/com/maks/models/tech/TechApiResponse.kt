package com.maks.models.tech

import com.fasterxml.jackson.annotation.JsonProperty

data class TechApiResponse(

    @JsonProperty("results")
    var results: List<TechPhoneData>? = null

)
