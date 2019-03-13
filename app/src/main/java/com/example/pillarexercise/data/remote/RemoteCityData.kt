package com.example.pillarexcercise.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteCityData(
    val id: String?,
    val name: String?,
    val coord: RemoteCoordinate?,
    val country: String?
)