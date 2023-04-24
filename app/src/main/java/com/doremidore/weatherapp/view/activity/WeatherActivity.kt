package com.doremidore.weatherapp.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Debug
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.doremidore.weatherapp.R
import com.doremidore.weatherapp.databinding.ActivityWeatherBinding
import com.doremidore.weatherapp.model.WeatherModel
import com.doremidore.weatherapp.model.WeatherYandexInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //Debug.waitForDebugger()
        super.onCreate(savedInstanceState)

        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportFragmentManager
        //    .beginTransaction()
        //    .replace(R.id.placeHolder, WeatherFragment.newInstance()).commit()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "PERMISSIONS", Toast.LENGTH_LONG).show()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val location = fusedLocationClient.lastLocation.await()
            val weatherList = getReuslt(location.latitude.toString(), location.longitude.toString())
            runOnUiThread {
                binding.tvText.text = weatherList?.geo_object?.locality?.name
            }
        }
    }

    suspend fun getReuslt(lat: String, lon: String): WeatherModel? {
        val BASE_URL = "https://api.weather.yandex.ru/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherYandexInterface::class.java)

        return weatherApi.getWeather(lat, lon)
    }
}
