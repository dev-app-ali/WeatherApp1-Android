package com.example.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    const val BASE_URL = "https://api.weatherapi.com"

    private fun getInstance(): Retrofit {

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    val weatherApi: WeatherAPI = getInstance().create(WeatherAPI ::class.java)
}