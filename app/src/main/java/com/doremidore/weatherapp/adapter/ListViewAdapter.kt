package com.doremidore.weatherapp.adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.doremidore.weatherapp.R
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doremidore.weatherapp.Utils.convertDateToCustomFormat
import com.doremidore.weatherapp.databinding.ListViewItemBinding
import com.doremidore.weatherapp.model.WeatherModel
import com.doremidore.weatherapp.model.WeatherModelsClass.Forecast


class ListViewAdapter: ListAdapter<Forecast, ListViewAdapter.Holder>(Comporator()){

    class Holder(view: View) : RecyclerView.ViewHolder(view){
            private val binding = ListViewItemBinding.bind(view)
            fun bind(item: Forecast) = with(binding){
                tvItemDay.text =  item.date
                tvItemCondition.text = item.parts.day.condition
                tvItemDayTemp.text = item.parts.day.temp_avg.toString() + "°C"
                tvItemNightTemp.text = item.parts.night.temp_avg.toString() + "°C"
                ivItemPrecipitation.setImageDrawable(item.parts.day.iconDrawable)  //TODO ImageView
            }
        }

    class Comporator : DiffUtil.ItemCallback<Forecast>(){
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_view_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}