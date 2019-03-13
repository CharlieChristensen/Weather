package com.example.pillarexcercise.data

import com.example.pillarexcercise.data.remote.RemoteForecast
import com.example.pillarexcercise.data.ui.Forecast
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(retrofit: Retrofit) {

    private val api: Api = retrofit.create(Api::class.java)

    fun getWeatherDataWithCityName(cityName: String): Single<Forecast> =
        api.getWeatherDataWithCityName("$cityName,us")
            .map { it.toUi() }

    fun getWeatherDataWithZipCode(
        zipCode: String
    ): Single<Forecast> = api.getWeatherDataWithZipCode(zipCode)
        .map { it.toUi() }

    fun getWeatherDataWithLatLong(
        latitude: String,
        longitude: String
    ): Single<Forecast> = api.getWeatherDataWithLatLong(latitude, longitude)
        .map { it.toUi() }

    interface Api {

        @GET("forecast")
        fun getWeatherDataWithCityName(
            @Query("q") cityName: String
        ): Single<RemoteForecast>

        @GET("forecast")
        fun getWeatherDataWithZipCode(
            @Query("zip") zipCode: String
        ): Single<RemoteForecast>

        @GET("forecast")
        fun getWeatherDataWithLatLong(
            @Query("lat") latitude: String,
            @Query("lon") longitude: String
        ): Single<RemoteForecast>

    }

}