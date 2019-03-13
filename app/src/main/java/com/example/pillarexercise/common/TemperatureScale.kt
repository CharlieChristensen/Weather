package com.example.pillarexcercise.common

sealed class TemperatureScale {
    object Fahrenheit: TemperatureScale()
    object Celsius: TemperatureScale()
}