package com.example.pillarexcercise.weatherscreen

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.pillarexcercise.R
import com.example.pillarexcercise.common.BaseFragment
import com.example.pillarexcercise.common.TemperatureScale.Celsius
import com.example.pillarexcercise.common.TemperatureScale.Fahrenheit
import com.example.pillarexcercise.data.ui.Forecast
import com.example.pillarexcercise.di.AppComponent
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment: BaseFragment<WeatherViewModel>(), OnChartValueSelectedListener {

    override fun getLayoutResource(): Int = com.example.pillarexcercise.R.layout.fragment_weather

    override fun getViewModelClass(): Class<WeatherViewModel> = WeatherViewModel::class.java

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val forecast: Forecast = arguments?.getParcelable(ARG_FORECAST) ?: error("Must pass Forecast object to WeatherFragment")
        viewModel.inputs.setForecast(forecast)

        temperatureToggle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.text){
                    "F" -> viewModel.inputs.temperatureScaleToggled(Fahrenheit)
                    "C" -> viewModel.inputs.temperatureScaleToggled(Celsius)
                }
            }
        })

        chart.setOnChartValueSelectedListener(this)
        chart.setNoDataText(getString(R.string.loading))
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
        chart.setScaleEnabled(false)
        chart.xAxis.setDrawLabels(false)

        viewModel.outputs.chartEntries()
            .bind {
                chart.data = it
                chart.invalidate()
            }

        viewModel.outputs.cityName()
            .bind { cityNameTextView.text = it }

        viewModel.outputs.temperature()
            .bind { temperatureTextView.text = it }

        viewModel.outputs.humidity()
            .bind { humidityTextView.text = it }

        viewModel.outputs.pressure()
            .bind { pressureTextView.text = it }

        viewModel.outputs.date()
            .bind { dateTextView.text = it }

    }

    override fun onNothingSelected() {

    }

    override fun onValueSelected(e: Entry, h: Highlight) {
//        Log.d("CHARTT", h.toString())
//        val x = chart.xAxis.valueFormatter.getFormattedValue(e.x, chart.xAxis)
        viewModel.inputs.onItemSelected(h.x)
    }

    companion object {
        private const val ARG_FORECAST = "ArgForecast"

        fun newInstance(forecast: Forecast): WeatherFragment {
            val fragment = WeatherFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(ARG_FORECAST, forecast)
            }
            return fragment
        }
    }

}