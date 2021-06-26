package com.example.kotlincoroutinesretrofitexample.weather

import com.example.kotlincoroutinesretrofitexample.data.Forecast

sealed interface WeatherResult { // изолированый класс с двумя дата классами с контентом в листе
// и классом ошибок

    data class Content(val forecast: List<Forecast>): WeatherResult
    data class Error(val throwable: Throwable): WeatherResult
}