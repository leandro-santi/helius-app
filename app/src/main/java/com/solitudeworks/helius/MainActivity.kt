package com.solitudeworks.helius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.solitudeworks.helius.databinding.ActivityMainBinding
import com.solitudeworks.helius.model.OpenWeatherReponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {

        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "77bf36cd5fba7298ac757c9da0d752ac"

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()

    }

    private fun setListeners() {

        binding.btSearch.setOnClickListener {
            getWeather()
        }

    }

    private fun getWeather() {

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(OpenWeatherInterface::class.java)
        val call = service.getCurrentWeatherByCity(binding.etCity.text.toString(), API_KEY)

        call.enqueue(object : Callback<OpenWeatherReponse> {
            override fun onResponse(
                call: Call<OpenWeatherReponse>?,
                response: Response<OpenWeatherReponse>?
            ) {

                if (response?.code() == 200) {
                    val responseWeather = response.body()!!

                    val city = responseWeather.name
                    val weather =
                        responseWeather.weather[0].main + " " + "(${responseWeather.weather[0].description})"
                    val temperature = responseWeather.main.temp.toString() + " °C"
                    val feelsLike = responseWeather.main.feelsLike.toString() + " °C"

                    binding.tvCityTitleValue.text = city

                    binding.tvWeatherTitleValue.text = weather

                    binding.tvTemperatureTitleValue.text = temperature

                    binding.tvThermalTitleValue.text = feelsLike
                }

            }

            override fun onFailure(call: Call<OpenWeatherReponse>?, t: Throwable?) {

                Toast.makeText(baseContext, t?.message, Toast.LENGTH_SHORT).show()

            }

        })

    }

}