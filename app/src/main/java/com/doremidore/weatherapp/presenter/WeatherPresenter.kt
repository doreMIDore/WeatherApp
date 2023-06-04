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
    private var weatherData: WeatherModel? = null


    override fun showRecyclerViewData(){
        view.initRcView(weatherData?.forecasts)
    }


    private suspend fun loadItemIcon(weatherData: WeatherModel?) {
        coroutineScope{
        if (weatherData != null) {
            val deferred = async(Dispatchers.IO) {
                for (i in weatherData.forecasts){
                    i.parts.day.iconDrawable = repository.getImage(i.parts.day.icon)
                }
            }
            deferred.await()
        }
        }
    }


    override fun loadDataWeather(lat: String, lon: String) {
        launch(Dispatchers.Main) {
            try {
                val dataWeather = withContext(Dispatchers.IO) {
                    repository.getWeatherData(lat, lon)
                }
                for (i in dataWeather.forecasts){
                    i.date = convertDateToCustomFormat(i.date)
                    i.parts.day.condition = translateCondition(i.parts.day.condition,
                        view.getContext())
                }
                view.fillDataWeatherCard(dataWeather)
                loadImg(dataWeather.fact.icon)
                loadItemIcon(dataWeather)
                view.initRcView(dataWeather.forecasts)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadImg(path: String) {
        launch(Dispatchers.Main) {
            try {
                val drawable = withContext(Dispatchers.IO) {
                    repository.getImage(path) // TODO "getLocation()"
                }
                view.fillImageWeatherCard(drawable)

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    override val coroutineContext: CoroutineContext = mainJob

}