package com.example.kotlincoroutinesretrofitexample.weather

import com.example.kotlincoroutinesretrofitexample.data.Forecast


class WeatherResponse {
    val properties: Properties? = null // проперти из дата класса изначально равны ничему

    class Properties {
        val periods: List<Forecast>? = null  // полученную информацию оборачиваем в лист
    }


}