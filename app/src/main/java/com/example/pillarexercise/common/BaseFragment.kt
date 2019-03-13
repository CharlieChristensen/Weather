package com.example.pillarexcercise.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.pillarexcercise.PillarApplication
import com.example.pillarexcercise.di.AppComponent
import com.example.pillarexcercise.di.ViewModelFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

abstract class BaseFragment<VM: BaseViewModel>: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    protected var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        val application = requireActivity().application as PillarApplication
        inject(application.injector)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(getViewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(getLayoutResource(), container, false)

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    protected fun addFragment(
        fragment: Fragment,
        addToBackstack: Boolean = true
//        animations: FragmentAnimations
    ) {
        val activity = activity
        if (activity is BaseActivity) {
            activity.addFragment(
                fragment,
                addToBackstack
            )
        }
    }

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    protected abstract fun inject(appComponent: AppComponent)

    protected abstract fun getViewModelClass(): Class<VM>

    private val onNextStub: (Any) -> Unit = {}
    private val onErrorStub: (Throwable) -> Unit = {
        RxJavaPlugins.onError(
            OnErrorNotImplementedException(it)
        )
    }
    private val onCompleteStub: () -> Unit = {}

    fun <T : Any> Observable<T>.bind(
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (T) -> Unit = onNextStub
    ) {
        this.observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, onError, onComplete)
            .addTo(disposables)
    }

}