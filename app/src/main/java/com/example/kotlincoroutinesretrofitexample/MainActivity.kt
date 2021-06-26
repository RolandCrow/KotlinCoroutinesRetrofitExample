package com.example.kotlincoroutinesretrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.*
import com.example.kotlincoroutinesretrofitexample.data.MainViewState
import com.example.kotlincoroutinesretrofitexample.data.RowState
import com.example.kotlincoroutinesretrofitexample.databinding.ActivityMainBinding
import com.example.kotlincoroutinesretrofitexample.databinding.RowBinding
import com.example.kotlincoroutinesretrofitexample.viewmodel.MainMotorViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = ActivityMainBinding.inflate(layoutInflater) // присоединяем активити через
        // биндинг к
        // лейауту

        setContentView(binding.root)

        val motor: MainMotorViewModel by viewModels() // присоединяем вью модел
        val adapter = WeatherAdapter() // присоединяем адаптер ресайклер вью

        binding.forecast.layoutManager = LinearLayoutManager(this) // наполняем разметку
        binding.forecast.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        ) // работа с разметкой через дата биндинг

        binding.forecast.adapter = adapter // подключаем ресайклер вью

        motor.results.observe(this) { state ->
            when (state) {
                MainViewState.Loading -> binding.progress.visibility = View.VISIBLE // если
                // данные загружаются то показываем прогресс в прогрессабаре после загрузки
                // данных бар исчезает
                is MainViewState.Content -> {
                    binding.progress.visibility = View.GONE
                    adapter.submitList(state.forecast)
                }
                is MainViewState.Error -> { // выводим ошибку если такая происходит
                    binding.progress.visibility = View.GONE
                    Toast.makeText(this, state.throwable.localizedMessage, Toast.LENGTH_LONG).show()
                    Log.e("Weather", "Exception loading data", state.throwable)
                }
            }
        }

        motor.load("OKX", 32,32) // дефолтное значение координат

    }


    inner class WeatherAdapter : ListAdapter<RowState, RowHolder>(RowStateDiffer) { // принимает
        // лист адаптер из ресайклер вью и дата биндинга
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
            return RowHolder(RowBinding.inflate(layoutInflater, parent, false))

        }

        override fun onBindViewHolder(holder: RowHolder, position: Int) {
            holder.bind(getItem(position))
        }


    }

    class RowHolder(private val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) { //
        // присоединяем ресайклер вью с дата биндингом
        fun bind(state: RowState) { // биндим дата класс
            binding.state = state
            binding.executePendingBindings()
        }
    }

    object RowStateDiffer : DiffUtil.ItemCallback<RowState>() { // проверка одинаковости, чтобы не
        // загружать данные еще раз
        override fun areItemsTheSame(oldItem: RowState, newItem: RowState): Boolean {
            return oldItem == newItem

        }

        override fun areContentsTheSame(oldItem: RowState, newItem: RowState): Boolean {
            return oldItem == newItem
        }

    }


}




