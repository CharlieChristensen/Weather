package com.example.pillarexcercise.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteForecastListItem(
    val dt: Long,
    val main: RemoteForecastMainData

)