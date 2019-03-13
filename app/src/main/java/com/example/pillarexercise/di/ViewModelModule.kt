package com.example.pillarexcercise.di

import androidx.lifecycle.ViewModel
import com.example.pillarexcercise.homescreen.HomescreenViewModel
import com.example.pillarexcercise.weatherscreen.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomescreenViewModel::class)
    abstract fun bindsHomescreenViewModel(viewModel: HomescreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindsWeatherViewModel(viewModel: WeatherViewModel): ViewModel

}