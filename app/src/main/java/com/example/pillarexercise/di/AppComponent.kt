package com.example.pillarexcercise.di

import android.app.Application
import com.example.pillarexcercise.MainActivity
import com.example.pillarexcercise.homescreen.HomescreenFragment
import com.example.pillarexcercise.weatherscreen.WeatherFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomescreenFragment)
    fun inject(fragment: WeatherFragment)

}