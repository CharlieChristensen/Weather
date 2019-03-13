package com.example.pillarexcercise.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteForecastMainData(
    val temp: Float?,
    val temp_min: Float?,
    val temp_max: Float?,
    val pressure: Float?,
    val sea_level: Float?,
    val grnd_level: Float?,
    val humidity: Int?,
    val temp_kf: Float?
)