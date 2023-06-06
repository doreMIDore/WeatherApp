package com.doremidore.weatherapp.presenter

import com.doremidore.weatherapp.Utils.convertDateToCustomFormat
import com.doremidore.weatherapp.Utils.translateCondition
import com.doremidore.weatherapp.model.Repository.WeatherRepository
import com.doremidore.weatherapp.model.Repository.WeatherRepositoryInterface
import com.doremidore.weatherapp.model.WeatherModel
import com.doremidore.weatherapp.view.activity.WeatherActivityInterface
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class WeatherPresenter(
    private val view: WeatherActivityInterface,
) : PresenterInterface, CoroutineScope {

    private val repository: WeatherRepositoryInterface = WeatherRepository()
    private val mainJob = SupervisorJob()

    /**
     * Обрабатывает данные погоды. Выполняет запрос на получение данных погоды
     * [WeatherRepository.getWeatherData].
     * Конвертирует данные даты [convertDateToCustomFormat] и описания погоды [translateCondition].
     * Заполняет данными WeatherCard [WeatherActivityInterface.fillDataWeatherCard].
     * Выполняет запрос на получение данных иконки погоды [loadImg].
     * Выполняет запрос на получение данных иконки погоды элементов списка [loadItemIcon].
     * Инициализирует RecyclerView [WeatherActivityInterface.initRcView].
     * В случае ошибки выводит [WeatherActivityInterface.showErrorNetwork]
     * */
    override fun processWeatherData(lat: String, lon: String) {
        launch(Dispatchers.Main) {
            try {
                val dataWeather = withContext(Dispatchers.IO) {
                    repository.getWeatherData(lat, lon)
                }
                for (i in dataWeather.forecasts){
                    i.date = convertDateToCustomFormat(i.date)
                    i.parts.day.condition = translateCondition(i.parts.day.condition,
                        view.getRes())
                }

                view.fillDataWeatherCard(dataWeather)
                loadImg(dataWeather.fact.icon)
                loadItemIcon(dataWeather)
                view.initRcView(dataWeather.forecasts)

            } catch (e: Exception) {
                e.printStackTrace()
                view.showErrorNetwork()
            }
        }
    }

    /**
     * Выполняет запрос на получение изображения иконки погоды [WeatherRepository.getImage]
     * Заполняет изображение в WeatherCard [WeatherActivityInterface.fillDataWeatherCard].
     * В случае ошибки выводит [WeatherActivityInterface.showErrorNetwork]
     * */
    override fun loadImg(path: String) {
        launch(Dispatchers.Main) {
            try {
                val drawable = withContext(Dispatchers.IO) {
                    repository.getImage(path)
                }
                view.fillImageWeatherCard(drawable)

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                view.showErrorNetwork()
            }
        }
    }

    /**
     * Выполняет запрос на получение изображения иконки погоды для каждого дня недели
     * [WeatherRepository.getImage]
     * В случае ошибки выводит [WeatherActivityInterface.showErrorNetwork]
     * */
    private suspend fun loadItemIcon(weatherData: WeatherModel) {
        coroutineScope{
            try {
                val deferred = async(Dispatchers.IO) {
                    for (i in weatherData.forecasts){
                        i.parts.day.iconDrawable = repository.getImage(i.parts.day.icon)
                    }
                }
                deferred.await()
            } catch (e: Exception) {
                e.printStackTrace()
                view.showErrorNetwork()
            }
        }
    }

    override val coroutineContext: CoroutineContext = mainJob

}