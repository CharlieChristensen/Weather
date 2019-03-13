package com.example.pillarexcercise.data.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    val cityName: String,
    val weatherList: List<WeatherData>
): Parcelable