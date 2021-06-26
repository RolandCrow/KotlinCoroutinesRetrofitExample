package com.example.kotlincoroutinesretrofitexample.data

data class Forecast( // дата класс с методом копи для создания новых экземпляров класса
    val name: String,
    val temperature: Int,
    val temperatureUnit: String,
    val icon: String // икона в виде url для глайда

)

