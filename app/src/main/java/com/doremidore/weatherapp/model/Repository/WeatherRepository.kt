package com.doremidore.weatherapp.model.Repository

import android.graphics.drawable.PictureDrawable
import com.caverock.androidsvg.SVG
import com.doremidore.weatherapp.model.ImageInterface
import com.doremidore.weatherapp.model.WeatherDataInterface
import com.doremidore.weatherapp.model.WeatherModel

class WeatherRepository(private val data: WeatherDataInterface,
                        private val imageData : ImageInterface) : WeatherRepositoryInterface {

    private lateinit var weatherData: WeatherModel

    override suspend fun getWeatherData(lat: String, lon: String): WeatherModel {
        weatherData = data.requestWeatherData(lat, lon)
        return weatherData
    }

    override fun getData() = weatherData

    override suspend fun getImage(iconPath : String): PictureDrawable {
        val image = imageData.requestSvgImage(iconPath)
        val svg = SVG.getFromInputStream(image.byteStream())

        return PictureDrawable(svg.renderToPicture())
    }

    companion object {
        private var instance: WeatherRepository? = null

         operator fun invoke(): WeatherRepositoryInterface{
            var localIns = instance
            if (localIns == null){
                val data = WeatherDataInterface()
                val img = ImageInterface()
                localIns = WeatherRepository(data, img)
                instance = localIns
            }

             return localIns
        }
    }
}

