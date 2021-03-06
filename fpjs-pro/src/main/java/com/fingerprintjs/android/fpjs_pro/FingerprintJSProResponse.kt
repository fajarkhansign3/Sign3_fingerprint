package com.fingerprintjs.android.fpjs_pro


data class FingerprintJSProResponse(
    val requestId: String,
    val visitorId: String,
    val confidenceScore: ConfidenceScore,
    val visitorFound: Boolean,
    val ipAddress: String,
    val ipLocation: IpLocation?,
    val osName: String,
    val osVersion: String,
    val errorMessage: String? = null,
    val gsfId: String,
    val androidId: String,
    val mediaDrmId: String,
    val s67: String
)

data class IpLocation(
    val accuracyRadius: Int,
    val latitude: Double,
    val longitude: Double,
    val postalCode: String,
    val timezone: String,
    val city: City,
    val country: Country,
    val continent: Continent,
    val subdivisions: List<Subdivisions>
) {

    data class City(
        val name: String
    )

    data class Country(
        val code: String,
        val name: String
    )

    data class Continent(
        val code: String,
        val name: String
    )

    data class Subdivisions(
        val isoCode: String,
        val name: String
    )
}

data class ConfidenceScore(
    val score: Double
)

