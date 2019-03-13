package com.example.pillarexcercise.homescreen

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pillarexcercise.R
import com.example.pillarexcercise.common.BaseFragment
import com.example.pillarexcercise.di.AppComponent
import com.example.pillarexcercise.weatherscreen.WeatherFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

class HomescreenFragment : BaseFragment<HomescreenViewModel>(),
    SearchHistoryAdapter.SearchHistoryCallback {
    private val searchHistoryAdapter = SearchHistoryAdapter(this)
    private lateinit var progressDialog: ProgressDialog

    override fun getLayoutResource(): Int = R.layout.fragment_home

    override fun getViewModelClass(): Class<HomescreenViewModel> = HomescreenViewModel::class.java

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(context)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = searchHistoryAdapter

        searchCityButton.setOnClickListener {
            viewModel.inputs.searchWithCityName(cityTextField.text.toString())
        }

        searchZipCodeButton.setOnClickListener {
            viewModel.inputs.searchWithZipCode(zipTextField.text.toString())
        }

        searchLatLongButton.setOnClickListener {
            viewModel.inputs.searchWithLatLong(
                latitudeTextField.text.toString(),
                longitudeTextField.text.toString()
            )
        }
        viewModel.outputs.showForecast()
            .bind { addFragment(WeatherFragment.newInstance(it)) }

        viewModel.outputs.searchHistory()
            .bind { searchHistoryAdapter.submitList(it) }

        viewModel.outputs.progress()
            .bind {
                if (it) {
                    progressDialog.show()
                } else {
                    progressDialog.hide()
                }
            }

        viewModel.outputs.error()
            .bind {
                Snackbar.make(view, it, 3000).show()
            }

    }

    override fun onItemClicked(index: Int) {
        viewModel.inputs.onClickSearchHistoryItem(index)
    }

}