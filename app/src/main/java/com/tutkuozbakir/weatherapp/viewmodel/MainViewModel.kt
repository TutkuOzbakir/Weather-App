package com.tutkuozbakir.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tutkuozbakir.weatherapp.model.WeatherModel
import com.tutkuozbakir.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel() : ViewModel() {

    private val weatherAPIService = WeatherAPIService()
    private val disposable = CompositeDisposable()

    var weatherData = MutableLiveData<WeatherModel>()
    var error = MutableLiveData<Boolean>()
    var loadState = MutableLiveData<Boolean>()

    fun refreshData(city: String){
        getDataFromAPI(city)
    }

    private fun getDataFromAPI(city: String){
        loadState.value = true
        disposable.add(weatherAPIService.getDataService(city)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<WeatherModel>(){
                override fun onSuccess(t: WeatherModel) {
                    weatherData.value = t
                    error.value = false
                    loadState.value = false

                }
                override fun onError(e: Throwable) {
                    error.value = true
                    loadState.value = false
                }

            }))

    }
}