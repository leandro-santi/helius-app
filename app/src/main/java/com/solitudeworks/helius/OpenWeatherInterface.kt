package com.solitudeworks.helius

import com.solitudeworks.helius.model.OpenWeatherReponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherInterface {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("APPID") appId: String,
        @Query("units") units: String = "metric"
    ): Call<OpenWeatherReponse>

}