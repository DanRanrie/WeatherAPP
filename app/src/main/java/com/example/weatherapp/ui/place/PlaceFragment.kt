package com.example.weatherapp.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.WeatherActivity
import com.example.weatherapp.WeatherApplication
import kotlin.math.log

class PlaceFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter: PlaceAdapter
    // 鉴于Fragment不能获得控件对象
    // 而且在onCreateView中获得的对象，也不能添加监听事件
    // 所以这里采用延迟初始化变量方法
    lateinit var recyclerView : RecyclerView
    lateinit var searchPlaceEdit : EditText
    lateinit var bgImageView : ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragmentlayout, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchPlaceEdit = view.findViewById(R.id.searchPlaceEdit)
        bgImageView = view.findViewById(R.id.bgImageView)
        return view
    }


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        val layoutManager = LinearLayoutManager(activity)
//        recyclerView.layoutManager = layoutManager
//        adapter = PlaceAdapter(this, viewModel.LivesList)
//        recyclerView.adapter = adapter
//        // 莫名其妙的addTextChangedListener报错，重新 . 点引用一下就好了
//        searchPlaceEdit.addTextChangedListener {
//            editable ->
//            val content = editable.toString()
//            if (content.isNotEmpty()) {
//                viewModel.searchPlaces(content)
//            } else {
//                recyclerView.visibility = View.GONE
//                bgImageView.visibility = View.VISIBLE
//                viewModel.LivesList.clear()
//                adapter.notifyDataSetChanged()
//            }
//        }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewModel.isCitySaved()){
            val place = viewModel.getSavedCity()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("city", place.city)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
    val layoutManager = LinearLayoutManager(activity)
    recyclerView.layoutManager = layoutManager
    adapter = PlaceAdapter(this, viewModel.LivesList)
    recyclerView.adapter = adapter
    // 莫名其妙的addTextChangedListener报错，重新 . 点引用一下就好了
    searchPlaceEdit.addTextChangedListener {
        editable ->
        val content = editable.toString()
        if (content.isNotEmpty()) {
            viewModel.searchPlaces(content)
        } else {
            recyclerView.visibility = View.GONE
            bgImageView.visibility = View.VISIBLE
            viewModel.LivesList.clear()
            adapter.notifyDataSetChanged()
        }
    }
    // 这里不让用this，可能Fragment不给当生命周期使用
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer{ result ->
            val lives = result.getOrNull()
            if (lives != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.LivesList.clear()
                viewModel.LivesList.addAll(lives)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}