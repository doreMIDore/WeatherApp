package com.doremidore.weatherapp.view.fragments

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.caverock.androidsvg.SVG
import com.doremidore.weatherapp.Utils.translateCondition

import com.doremidore.weatherapp.adapter.ViewPagerAdapter
import com.doremidore.weatherapp.databinding.FragmentWeatherBinding
import com.doremidore.weatherapp.model.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherFragment : Fragment() {
    private val flist = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tlist = listOf(
        "Часы",
        "Дни"
    )
    private lateinit var pLauncher : ActivityResultLauncher<String>
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Debug.waitForDebugger()
        checkPermission()
        init()
        CoroutineScope(Dispatchers.IO).launch {
            val weatherData = getWeatherData()
            val icon = getImage(weatherData)
            activity?.runOnUiThread {
               icon?.let { fillWeatherCard(weatherData, it) }
            }
        }

    }

    private fun init() = with(binding){
        val adapter = ViewPagerAdapter(activity as FragmentActivity, flist)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager){
            tab, pos -> tab.text = tlist[pos]
        }.attach()
    }

    private fun permissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(){
        if (!isPermissionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun fillWeatherCard(weatherData : WeatherModel?, drawable: PictureDrawable) = with(binding){
        tvCity.text = weatherData?.geo_object?.province?.name
        val condition = translateCondition(weatherData?.fact?.condition
            ?: "Неизвестые погодные условия" , context)
        tvCondition.text = condition
        ivWeather.setImageDrawable(drawable)
        tvTemp.text = weatherData?.fact?.temp.toString() + "°C"
    }

    private suspend fun getImage(weatherData: WeatherModel?) : PictureDrawable{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://yastatic.net/weather/i/icons/funky/dark/")
            .build()

        val imgService = retrofit.create(ImageInterface::class.java)

        val call = weatherData?.fact?.let { imgService.requestSvgImage(it.icon) }
        val svg = SVG.getFromInputStream(call?.byteStream())
        return PictureDrawable(svg.renderToPicture())
    }

     private suspend fun getWeatherData(lat: String = "55.751244", lon: String = "37.618423"): WeatherModel? {
        val BASE_URL = "https://api.weather.yandex.ru/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherDataInterface::class.java)
         print(weatherApi.requestWeatherData(lat, lon))
         Log.d("Log", "result: ${weatherApi.requestWeatherData(lat, lon)}")
         return weatherApi.requestWeatherData(lat, lon)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherFragment()
    }
}