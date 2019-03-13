package com.example.pillarexcercise.homescreen

import com.example.pillarexcercise.R
import com.example.pillarexcercise.common.BaseViewModel
import com.example.pillarexcercise.data.Repository
import com.example.pillarexcercise.data.ui.Forecast
import com.example.pillarexcercise.homescreen.HomescreenContract.Inputs
import com.example.pillarexcercise.homescreen.HomescreenContract.Outputs
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface HomescreenContract {

    interface Inputs {
        fun searchWithCityName(cityName: String)
        fun searchWithZipCode(zipcode: String)
        fun searchWithLatLong(latitude: String, longitude: String)
        fun onClickSearchHistoryItem(index: Int)
    }

    interface Outputs {
        fun showForecast(): Observable<Forecast>
        fun searchHistory(): Observable<List<SearchHistoryListItem>>
        fun error(): Observable<Int>
        fun progress(): Observable<Boolean>
    }

}

class HomescreenViewModel @Inject constructor(
    private val repository: Repository
): BaseViewModel(), Inputs, Outputs {

    private val searchHistoryList = mutableListOf<SearchHistoryListItem>()

    private val showForecastRelay = PublishRelay.create<Forecast>()
    private val searchHistoryRelay = BehaviorRelay.create<List<SearchHistoryListItem>>()
    private val progressRelay = BehaviorRelay.create<Boolean>()
    private val errorRelay = PublishRelay.create<Int>()

    val inputs: Inputs = this
    val outputs: Outputs = this

    //region Inputs

    override fun searchWithCityName(cityName: String) {
        saveSearchHistory(SearchHistoryListItem.City(cityName))
        repository.getWeatherDataWithCityName(cityName)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressRelay.accept(true) }
            .doFinally { progressRelay.accept(false) }
            .subscribeBy(
                onSuccess = { showForecastRelay.accept(it) },
                onError = { errorRelay.accept(R.string.cant_find_data_error) }
            )
            .addTo(disposables)
    }

    override fun searchWithZipCode(zipcode: String){
        saveSearchHistory(SearchHistoryListItem.ZipCode(zipcode))
        repository.getWeatherDataWithZipCode(zipcode)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressRelay.accept(true) }
            .doFinally { progressRelay.accept(false) }
            .subscribeBy(
                onSuccess = { showForecastRelay.accept(it) },
                onError = { errorRelay.accept(R.string.cant_find_data_error) }
            )
            .addTo(disposables)
    }

    override fun searchWithLatLong(latitude: String, longitude: String) {
        saveSearchHistory(SearchHistoryListItem.LatLong(latitude, longitude))
        repository.getWeatherDataWithLatLong(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressRelay.accept(true) }
            .doFinally { progressRelay.accept(false) }
            .subscribeBy(
                onSuccess = { showForecastRelay.accept(it) },
                onError = { errorRelay.accept(R.string.cant_find_data_error) }
            )
            .addTo(disposables)
    }

    override fun onClickSearchHistoryItem(index: Int) {
        val item = searchHistoryList[index]
        when(item){
            is SearchHistoryListItem.City -> searchWithCityName(item.cityName)
            is SearchHistoryListItem.ZipCode -> searchWithZipCode(item.zipCode)
            is SearchHistoryListItem.LatLong -> searchWithLatLong(item.latitude, item.longitude)
        }
    }

    //endregion

    //region Outputs

    override fun showForecast(): Observable<Forecast> = showForecastRelay

    override fun searchHistory(): Observable<List<SearchHistoryListItem>> = searchHistoryRelay

    override fun progress(): Observable<Boolean> = progressRelay

    override fun error(): Observable<Int> = errorRelay

    //endregion

    private fun saveSearchHistory(item: SearchHistoryListItem){
        val size = searchHistoryList.size
        if (size == 3) {
            searchHistoryList.removeAt(size - 1)
        }
        searchHistoryList.add(0, item)
        searchHistoryRelay.accept(searchHistoryList.toList())
    }


}