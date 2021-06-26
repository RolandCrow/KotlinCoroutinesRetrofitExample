package com.example.kotlincoroutinesretrofitexample.api

import com.example.kotlincoroutinesretrofitexample.weather.WeatherResult
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WeatherRepository {

    private val api = Retrofit.Builder() // все для работы с сетью
        .baseUrl("https://api.weather.gov") // базовый адрес
        .addConverterFactory(MoshiConverterFactory.create()) // работа с json
        .build()
        .create(InterfaceApi::class.java) // запрос через основной интерфейс

    suspend fun load(office: String, gridX: Int, gridY: Int) = try { // загрузка данных через
        // корутины
        val response = api.getForecact(office, gridX, gridY) // присоединяем апи

        WeatherResult.Content(response.properties?.periods ?: listOf())  // получаем данные в
    // обертке листа


    } catch (t: Throwable) {
        WeatherResult.Error(t) // ловим ошибку если вдруг
    }


}