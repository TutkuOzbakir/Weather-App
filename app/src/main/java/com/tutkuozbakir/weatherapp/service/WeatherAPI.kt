package com.tutkuozbakir.weatherapp.service

import com.tutkuozbakir.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.openweathermap.org/data/2.5/weather?&units=metric&q=İzmir&appid=d19dd96347f807882c58514a4eb05faf
interface WeatherAPI {

    @GET("data/2.5/weather?&units=metric&q=İzmir&appid=d19dd96347f807882c58514a4eb05faf")
    fun getData(
        @Query("q") city: String
    ): Single<WeatherModel>
}