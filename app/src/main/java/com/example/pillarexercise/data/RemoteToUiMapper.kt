package com.example.pillarexcercise.data

import com.example.pillarexcercise.data.remote.RemoteForecast
import com.example.pillarexcercise.data.ui.Forecast
import com.example.pillarexcercise.data.ui.WeatherData
import java.util.*


fun RemoteForecast.toUi(): Forecast = Forecast(
    city?.name ?: "",
    list?.map {
        val main = it.main
        WeatherData(
            Date(it.dt * 1000L),
            main.temp ?: 0f,
            main.pressure ?: 0f,
            main.humidity ?: 0
        )
    } ?: listOf()
)