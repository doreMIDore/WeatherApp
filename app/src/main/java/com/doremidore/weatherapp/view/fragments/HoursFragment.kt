package com.doremidore.weatherapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.doremidore.weatherapp.R
import com.doremidore.weatherapp.Utils.translateCondition
import com.doremidore.weatherapp.adapter.ListViewAdapter
import com.doremidore.weatherapp.databinding.FragmentHoursBinding
import com.doremidore.weatherapp.databinding.FragmentWeatherBinding
import com.doremidore.weatherapp.model.WeatherItem
import com.doremidore.weatherapp.model.WeatherModel
import com.doremidore.weatherapp.model.WeatherModelsClass.Info

class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: ListViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    private fun initRcView() = with(binding){
        rvHours.layoutManager = LinearLayoutManager(activity)
        adapter = ListViewAdapter()
        rvHours.adapter = adapter
        //adapter.submitList(getHours())
    }

    private fun getHours(weatherData: WeatherModel): List<WeatherItem>{
        val list = mutableListOf<WeatherItem>()
        for (i in weatherData.forecasts[0].hours){
            val condition = translateCondition(i?.condition
                ?: "Неизвестые погодные условия" , context)
            val item = condition?.let {
                WeatherItem(
                    i.hour + " : 00",
                    it,
                    i.temp.toString(),
                    " "
                )

            }
            if (item != null) {
                list.add(item)
            }
        }
        return  list
    }
    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}