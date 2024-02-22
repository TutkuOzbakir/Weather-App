package com.tutkuozbakir.weatherapp.service

import com.tutkuozbakir.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//https://api.openweathermap.org/data/2.5/weather?&units=metric&q=İzmir&appid=d19dd96347f807882c58514a4eb05faf
class WeatherAPIService {
    private val BASE_URL = "https://api.openweathermap.org/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(city: String) : Single<WeatherModel>{
        return api.getData(city)
    }

}