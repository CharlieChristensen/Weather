package com.example.pillarexcercise

import android.app.Application
import com.example.pillarexcercise.di.AppComponent
import com.example.pillarexcercise.di.DaggerAppComponent

class PillarApplication: Application() {

    val injector: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}