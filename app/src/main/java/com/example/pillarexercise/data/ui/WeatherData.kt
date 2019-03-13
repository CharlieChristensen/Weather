package com.example.pillarexcercise.data.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class WeatherData(
    val time: Date,
    val temperature: Float,
    val pressure: Float,
    val humidity: Int
): Parcelable {

    val temperatureFahrenheit: Float
        get() = (temperature - 273.15f) * (9f / 5f) + 32f

    val temperatureCelsius: Float
        get() = temperature - 273.15f

}