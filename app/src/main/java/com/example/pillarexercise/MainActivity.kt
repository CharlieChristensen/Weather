package com.example.pillarexcercise

import android.os.Bundle
import com.example.pillarexcercise.common.BaseActivity
import com.example.pillarexcercise.di.AppComponent
import com.example.pillarexcercise.homescreen.HomescreenFragment

class MainActivity : BaseActivity() {

    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            addFragment(HomescreenFragment(), false)
        }
    }
}
