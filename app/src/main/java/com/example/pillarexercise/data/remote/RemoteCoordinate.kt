package com.example.pillarexcercise.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteCoordinate(
    val lon: String,
    val lat: String
)