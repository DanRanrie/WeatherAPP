package com.example.weatherapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.logic.model.Weather
import com.example.weatherapp.logic.model.getSky
import com.example.weatherapp.ui.place.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAPPTheme
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.MaterialCalendar

class WeatherActivity : ComponentActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.weatherlayout)
        if (viewModel.city.isEmpty()) {
            viewModel.city = intent.getStringExtra("city") ?: ""
        }
        viewModel.WeatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.city)
    }
    private fun showWeatherInfo(weather: Weather) {
        val placeName = findViewById<TextView>(R.id.placeName)
        placeName.text = viewModel.city
        val nowWeather = weather.nowWeather
        val forecasts = weather.forecasts
        //  填充nowweather布局中的数据
        val currentTempText = "${nowWeather[0].temperature} ℃"
        val currentTemp = findViewById<TextView>(R.id.currentTemp)
        currentTemp.text = currentTempText
        val currentSky = findViewById<TextView>(R.id.currentSky)
        currentSky.text = getSky(nowWeather[0].weather).info
        val currentPM25Text = "${nowWeather[0].province}"
        val currentAQI = findViewById<TextView>(R.id.currentAQI)
        currentAQI.text = currentPM25Text
        val reporttime = findViewById<TextView>(R.id.reporttime)
        reporttime.text = nowWeather[0].reporttime
        val nowLayout = findViewById<RelativeLayout>(R.id.nowLayout)
        val high = findViewById<TextView>(R.id.high)
        high.text = "最高气温：" + forecasts[0].casts[0].daytemp + "℃"
        val low = findViewById<TextView>(R.id.low)
        low.text = "最低气温：" + forecasts[0].casts[0].nighttemp + "℃"
        nowLayout.setBackgroundResource(getSky(nowWeather[0].weather).bg)
        //  填充forecastweather布局中的数据
        val forecastLayout = findViewById<LinearLayout>(R.id.forecastLayout)
        forecastLayout.removeAllViews()
        val days = forecasts[0].casts.size
        for (i in 0 until days) {
            val dayweather = forecasts[0].casts[i].dayweather
            val daytemp = forecasts[0].casts[i].daytemp
            val nighttemp = forecasts[0].casts[i].nighttemp
            val view = LayoutInflater.from(this).inflate(R.layout.forecastitem,
                forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = forecasts[0].casts[i].date
            dateInfo.text = simpleDateFormat.format(dayweather)
            val sky = getSky(dayweather)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${daytemp} ~ ${nighttemp} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val coldRiskText = findViewById<TextView>(R.id.coldRiskText)
        val dressingText = findViewById<TextView>(R.id.dressingText)
        val ultravioletText = findViewById<TextView>(R.id.ultravioletText)
        val carWashingText = findViewById<TextView>(R.id.carWashingText)
        val weatherLayout = findViewById<ScrollView>(R.id.weatherLayout)
        coldRiskText.text = nowWeather[0].humidity + "% RH"
        dressingText.text = nowWeather[0].winddirection + "风"
        ultravioletText.text = nowWeather[0].windpower + "级"
        carWashingText.text = nowWeather[0].weather
        weatherLayout.visibility = View.VISIBLE
    }
}
