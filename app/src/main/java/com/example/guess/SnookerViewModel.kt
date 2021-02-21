package com.example.guess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guess.data.Event
import com.example.guess.data.EventResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

// 建立生命週期即將要傾聽的ViewModel
class SnookerViewModel: ViewModel() {

    //當Activity觀察events有變動時，在Avcivity會得到通知而被執行
    //放入所有Event的List
    val events = MutableLiveData<List<Event>>()

    //連到網路並解析json，直接做初始化
    init {
        // ViewModel主要還是在主執行緒上動作，要讓它在另外的執行緒上才不會出錯
        viewModelScope.launch(Dispatchers.IO) {
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
            // 直接改變LifeData的events的value，因為不是在主執行緒動作，只能用postValue()背景動作
            events.postValue(Gson().fromJson(data, EventResult::class.java))
        }
    }
}