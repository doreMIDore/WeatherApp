package com.doremidore.weatherapp.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.doremidore.weatherapp.adapter.RecyclerViewAdapter
import com.doremidore.weatherapp.databinding.ActivityWeatherBinding
import com.doremidore.weatherapp.model.WeatherModel
import com.doremidore.weatherapp.model.WeatherModelsClass.Forecast
import com.doremidore.weatherapp.isPermissionGranted
import com.doremidore.weatherapp.presenter.PresenterInterface
import com.doremidore.weatherapp.presenter.WeatherPresenter
import com.doremidore.weatherapp.Utils.translateCondition
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource

class WeatherActivity : AppCompatActivity(), WeatherActivityInterface {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var adapter: RecyclerViewAdapter

    private var presenter: PresenterInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        checkPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        presenter = WeatherPresenter(this)

    }

    override fun onResume() {
        super.onResume()
        getLocation()

    }

    /**
     * Получение местоположения и выполение
     * [WeatherPresenter.processWeatherData]
     *
     * */
    private fun getLocation() {
        val cancellationToken = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            presenter?.processWeatherData("55.751244", "37.618423")
        } else {
            val currentLocationRequest = CurrentLocationRequest.Builder().build()
            fusedLocationClient
                .getCurrentLocation(currentLocationRequest, cancellationToken.token)
                .addOnCompleteListener {
                    if (it.result?.latitude == null) {
                        Toast.makeText(
                            this, "Включите местоположение на устройстве для " +
                                    "отображения корректных данных о погоде", Toast.LENGTH_LONG
                        ).show()
                    }
                    presenter?.processWeatherData(
                        it.result?.latitude?.toString() ?: "55.751244",
                        it.result?.longitude?.toString() ?: "37.618423"
                    )
                }
                .addOnFailureListener {
                    presenter?.processWeatherData("55.751244", "37.618423")
                }
        }
    }


    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it)
                Toast.makeText(
                    this,
                    "Предоставьте разрешение о местоположении устройства",
                    Toast.LENGTH_LONG
                ).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun fillDataWeatherCard(weatherData: WeatherModel?) =
        with(binding) {
            tvCity.text = weatherData?.geo_object?.locality?.name
            val condition = translateCondition(
                weatherData?.fact?.condition
                    ?: "Неизвестые погодные условия", resources
            )

            tvCondition.text = condition
            tvTemp.text = weatherData?.fact?.temp.toString() + "°C"
            if (weatherData?.fact?.temp_water == 0) {
                tvWaterTemp.visibility = View.INVISIBLE
                tvWaterValue.visibility = View.INVISIBLE
            } else
                tvWaterValue.text = weatherData?.fact?.temp_water.toString() + "°C"
            tvWindValue.text = weatherData?.fact?.wind_speed.toString() + " м/с"
            tvWetValue.text = weatherData?.fact?.humidity.toString() + "%"
        }

    override fun fillImageWeatherCard(drawable: PictureDrawable) =
        binding.ivWeather.setImageDrawable(drawable)

    override fun initRcView(weatherList: List<Forecast>?) = with(binding) {
        rvActivity.layoutManager = LinearLayoutManager(this@WeatherActivity)
        adapter = RecyclerViewAdapter()
        rvActivity.adapter = adapter
        adapter.submitList(weatherList)
    }


    override fun getRes(): Resources {
        return resources
    }


    override fun showErrorNetwork() {
        Toast.makeText(this, "Проверьте интернет подключение", Toast.LENGTH_LONG).show()
    }

}



