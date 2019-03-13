package com.example.pillarexcercise.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteForecast(
    val city: RemoteCityData?,
    val cod: Int?,
    val message: String?,
    val cnt: Int?,
    val list: List<RemoteForecastListItem>?
)