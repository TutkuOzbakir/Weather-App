package com.tutkuozbakir.weatherapp.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tutkuozbakir.weatherapp.R
import com.tutkuozbakir.weatherapp.databinding.ActivityMainBinding
import com.tutkuozbakir.weatherapp.model.WeatherModel
import com.tutkuozbakir.weatherapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        var cityname = sharedPreferences.getString("cityName", "İzmir")
        binding.editText.setText(cityname)
        viewModel.refreshData(cityname!!)

        getLiveData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressBar.visibility = View.GONE
            binding.textViewError.visibility = View.GONE

            var cityname = sharedPreferences.getString("cityName", "İzmir")
            binding.editText.setText(cityname)
            viewModel.refreshData(cityname!!)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.imageSearch.setOnClickListener {
            val cityname = binding.editText.text.toString()
            sharedPreferences.edit().putString("cityName", cityname).apply()
            viewModel.refreshData(cityname!!)
            getLiveData()
        }

    }

    private fun getLiveData() {

        viewModel.weatherData.observe(this, Observer { data ->
            data?.let{
                binding.progressBar.visibility = View.GONE
                binding.textViewDegree.text = data.main.temp.toString() + "°C"
                binding.textViewCountryCode.text = data.sys.country
                binding.textViewCityName.text = data.name.toString()
                binding.textViewHumidity.text = "Humidity: %" + data.main.humidity.toString()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(binding.imageSymbol)

            }
        })

        viewModel.loadState.observe(this, Observer { load ->
            load?.let{
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textViewError.visibility = View.GONE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }

        })

        viewModel.error.observe(this, Observer { error ->
            error?.let{
                if(it){
                    binding.progressBar.visibility = View.GONE
                    binding.textViewError.visibility = View.VISIBLE
                }else{
                    binding.textViewError.visibility = View.GONE
                }
            }

        })
    }
}