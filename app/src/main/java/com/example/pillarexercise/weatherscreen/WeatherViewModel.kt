package com.example.pillarexcercise.weatherscreen

import com.example.pillarexcercise.common.BaseViewModel
import com.example.pillarexcercise.common.TemperatureScale
import com.example.pillarexcercise.data.Repository
import com.example.pillarexcercise.data.ui.Forecast
import com.example.pillarexcercise.data.ui.WeatherData
import com.example.pillarexcercise.weatherscreen.WeatherViewModelContract.Inputs
import com.example.pillarexcercise.weatherscreen.WeatherViewModelContract.Outputs
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface WeatherViewModelContract {
    interface Inputs {
        fun temperatureScaleToggled(temperatureScale: TemperatureScale)
        fun setForecast(forecast: Forecast)
        fun onItemSelected(xValue: Float)
    }

    interface Outputs {
        fun chartEntries(): Observable<LineData>
        fun cityName(): Observable<String>
        fun temperature(): Observable<String>
        fun humidity(): Observable<String>
        fun pressure(): Observable<String>
        fun date(): Observable<String>
    }
}

class WeatherViewModel @Inject constructor() : BaseViewModel(), Inputs, Outputs {

    private val forecastRelay = BehaviorRelay.create<Forecast>()
    private lateinit var selectedItem: WeatherData
    private val temperatureScaleRelay =
        BehaviorRelay.createDefault<TemperatureScale>(TemperatureScale.Fahrenheit)
    private val chartEntriesRelay = BehaviorRelay.create<LineData>()
    private val cityNameRelay = BehaviorRelay.create<String>()
    private val temperatureRelay = BehaviorRelay.create<String>()
    private val humidityRelay = BehaviorRelay.create<String>()
    private val pressureRelay = BehaviorRelay.create<String>()
    private val dateRelay = BehaviorRelay.create<String>()

    private val dateFormatter = SimpleDateFormat("MMM dd hh:mm a", Locale.getDefault())

    val inputs: Inputs = this
    val outputs: Outputs = this

    init {
        Observables.combineLatest(
            temperatureScaleRelay,
            forecastRelay
        )
            .subscribeOn(Schedulers.io())
            .subscribeBy { (temperatureScale, forecast) ->
                buildChartData(temperatureScale, forecast)
            }
            .addTo(disposables)
    }

    private fun buildChartData(temperatureScale: TemperatureScale, forecast: Forecast) {
        val chartEntries = forecast.weatherList.mapIndexed { index, item ->
            when (temperatureScale) {
                TemperatureScale.Fahrenheit -> Entry(index.toFloat(), item.temperatureFahrenheit)
                TemperatureScale.Celsius -> Entry(index.toFloat(), item.temperatureCelsius)
            }
        }
        val dataSet = LineDataSet(chartEntries, "")
        dataSet.setDrawValues(false)
        dataSet.setDrawHorizontalHighlightIndicator(false)
        val lineData = LineData(dataSet)
        chartEntriesRelay.accept(lineData)
    }

    //region Inputs

    override fun setForecast(forecast: Forecast) {
        cityNameRelay.accept(forecast.cityName)
        forecastRelay.accept(forecast)
    }

    override fun temperatureScaleToggled(temperatureScale: TemperatureScale) {
        temperatureScaleRelay.accept(temperatureScale)
        if (this::selectedItem.isInitialized) {
            formatWeatherData(selectedItem)
        }
    }

    override fun onItemSelected(xValue: Float) {
        val forecast = forecastRelay.value ?: return
        val weatherData = forecast.weatherList.getOrNull(xValue.toInt()) ?: return
        selectedItem = weatherData
        formatWeatherData(selectedItem)
    }

    //endregion

    //region Outputs

    override fun chartEntries(): Observable<LineData> = chartEntriesRelay

    override fun cityName(): Observable<String> = cityNameRelay

    override fun temperature(): Observable<String> = temperatureRelay

    override fun humidity(): Observable<String> = humidityRelay

    override fun pressure(): Observable<String> = pressureRelay

    override fun date(): Observable<String> = dateRelay

    //endregion

    private fun formatWeatherData(weatherData: WeatherData) {
        val temperature = when (temperatureScaleRelay.value) {
            TemperatureScale.Fahrenheit -> weatherData.temperatureFahrenheit.toInt()
            TemperatureScale.Celsius -> weatherData.temperatureCelsius.toInt()
            else -> 0
        }
        temperatureRelay.accept("$temperature \u00B0")
        humidityRelay.accept("${weatherData.humidity}%")
        pressureRelay.accept("${weatherData.pressure} hPa")
        dateRelay.accept(dateFormatter.format(weatherData.time))
    }

}