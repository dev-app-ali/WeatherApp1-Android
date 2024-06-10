package com.example.weatherapp.api

sealed class NetworkResponse <T>{


    data class Success<T>(val data :T):NetworkResponse<T>()
    data class Error<T>(val message : String) :NetworkResponse<T>()
    data class  Loading <T>(val message :String): NetworkResponse<T>()


}