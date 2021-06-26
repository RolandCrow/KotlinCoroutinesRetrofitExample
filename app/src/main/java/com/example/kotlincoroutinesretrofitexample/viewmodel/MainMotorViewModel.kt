package com.example.kotlincoroutinesretrofitexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlincoroutinesretrofitexample.R
import com.example.kotlincoroutinesretrofitexample.api.WeatherRepository
import com.example.kotlincoroutinesretrofitexample.data.MainViewState
import com.example.kotlincoroutinesretrofitexample.data.RowState
import com.example.kotlincoroutinesretrofitexample.data.Scenario
import com.example.kotlincoroutinesretrofitexample.weather.WeatherResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainMotorViewModel(application: Application): AndroidViewModel(application) { // добавляем
// вью модель для модуля ретрофита для получения информации из репозитория

    private val _results = MutableLiveData<MainViewState>() // изменяемая лайв дата работает в
    // отдельном не главном потоке
// контента

    val results: LiveData<MainViewState> = _results // работа с дата классом в лайвдате вне
// основного потока

    fun load(office: String, gridX: Int, gridY: Int) {
        val scenario = Scenario(office, gridX, gridY)
        val current = _results.value

        if(current !is MainViewState.Content || current.scenario != scenario) { // если нет
        // сценария то он начинает загружаться
            _results.value = MainViewState.Loading // загрузка сценария

            viewModelScope.launch(Dispatchers.Main) {  // запускаем корутины в скопе
                // корутина вызывает лоад из репозитория и получает инфу из эмита с помощью лайв
                // даты
                val result = WeatherRepository.load(office, gridX, gridY) // загрузка всего

                _results.value = when(result) {
                    is WeatherResult.Content -> {
                        val rows = result.forecast.map { forecast -> // расположение полученных
                            // данных
                            val temp = getApplication<Application>()
                                .getString(
                                    R.string.temp,
                                    forecast.temperature,
                                    forecast.temperatureUnit
                                )
                            RowState(forecast.name,temp, forecast.icon)

                        }
                        MainViewState.Content(scenario, rows)
                    }
                    is WeatherResult.Error -> MainViewState.Error(result.throwable )
                }

            }

        }
    }





}