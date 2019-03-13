package com.example.pillarexcercise.common

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pillarexcercise.PillarApplication
import com.example.pillarexcercise.R
import com.example.pillarexcercise.di.AppComponent

abstract class BaseActivity: AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        val application = application as PillarApplication
        inject(application.injector)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
    }

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    protected abstract fun inject(appComponent: AppComponent)

    fun addFragment(
        fragment: Fragment,
        addToBackstack: Boolean = true
//        animations: FragmentAnimations = FragmentAnimations.SlideFromRight,
    ) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

//        when (animations) {
//            FragmentAnimations.SlideFromLeft -> transaction.setCustomAnimations(
//                R.anim.slide_enter_from_left,
//                R.anim.slide_exit_to_right,
//                R.anim.slide_enter_from_right,
//                R.anim.slide_exit_to_left
//            )
//            FragmentAnimations.SlideFromRight -> transaction.setCustomAnimations(
//                R.anim.slide_enter_from_right,
//                R.anim.slide_exit_to_left,
//                R.anim.slide_enter_from_left,
//                R.anim.slide_exit_to_right
//            )
//            FragmentAnimations.None -> {
//            }
//        }

        transaction.replace(R.id.fragmentContainer, fragment)
        if (addToBackstack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

}