package com.example.kotlincoroutinesretrofitexample.data

data class Scenario(val office: String, val gridX: Int, val gridY: Int)

sealed class MainViewState { // обновляет информацию после обновления основного потока

    object Loading : MainViewState()
    data class Content(val scenario: Scenario, val forecast: List<RowState>): MainViewState()

    data class Error(val throwable: Throwable): MainViewState()




}
