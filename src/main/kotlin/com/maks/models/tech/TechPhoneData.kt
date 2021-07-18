package com.maks.models.tech

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class TechPhoneData(
    @JsonProperty("objectId")
    var objectId: String? = null,

    @JsonProperty("Model")
    var model: String? = null,

    @JsonProperty("Brand")
    var brand: String? = null,

    @JsonProperty("Network")
    var network: String? = null,

    @JsonProperty("TwoG")
    var twoG: String? = null,

    @JsonProperty("ThreeG")
    var threeG: String? = null,

    @JsonProperty("FourG")
    var fourG: String? = null,

    @JsonProperty("Network_Speed")
    var networkSpeed: String? = null,

    @JsonProperty("GPRS")
    var gprs: String? = null,

    @JsonProperty("EDGE")
    var edge: String? = null
)
