package com.example.kotlincoroutinesretrofitexample.api

import com.example.kotlincoroutinesretrofitexample.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface InterfaceApi { // все для ретрофита

    @Headers("Accept: application/geo+json") // заголовок
    @GET("/gridpoints/{office}/{gridX},{gridY}/forecast") // полная ссылка на сервер с нужными
    // данными
    suspend fun getForecact(
        @Path("office") office: String, // данные берем изнутри приложения через путь
        @Path("gridX") gridX: Int,
        @Path("gridY") gridY: Int
        ): WeatherResponse // собираем в лист


}