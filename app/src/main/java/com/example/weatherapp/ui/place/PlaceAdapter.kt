package com.example.weatherapp.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.WeatherActivity
import com.example.weatherapp.logic.model.Lives


class PlaceAdapter(private val fragment: PlaceFragment, private val LivesList: List<Lives>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val province: TextView = view.findViewById(R.id.province)
        val city: TextView = view.findViewById(R.id.city)
        val weather: TextView = view.findViewById(R.id.weather)
        val temperature: TextView = view.findViewById(R.id.temperature)
        val humidity: TextView = view.findViewById(R.id.humidity)
        val reporttime: TextView = view.findViewById(R.id.reporttime)

    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.placeitem,
//            parent, false)
//        val holder = ViewHolder(view)
//        holder.itemView.setOnClickListener {
//            val position = holder.adapterPosition
//            val city = LivesList[position]
//            val intent = Intent(parent.context, WeatherActivity::class.java).apply {
//                putExtra("city", city.city)
//            }
//            fragment.startActivity(intent)
//            fragment.activity?.finish()
//        }
//        return holder
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.placeitem,
            parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = LivesList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                activity.findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawers()
                activity.viewModel.city = place.city
                activity.refreshWeather()
            }
            else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("city", place.city)
                }
                fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            fragment.viewModel.saveCity(place)
        }
        return holder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lives = LivesList[position]
        holder.province.text = lives.province
        holder.city.text = lives.city
        holder.weather.text = "一小时内天气情况："+lives.weather
        holder.temperature.text = "摄氏度："+lives.temperature+"°"
        holder.humidity.text = "湿度："+lives.humidity+"Kg/m3"
        holder.reporttime.text = "最新更新时间："+lives.reporttime
    }
    override fun getItemCount() = LivesList.size
}