package com.example.guess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


// 使用ViewModel必須要繼承ViewModel父類別
class GuessViewModel : ViewModel() {
    // 建立counter的LiveData可變清單
    // 當Activity觀察counter有變動時，在Activity會得到通知而被執行
    val counter = MutableLiveData<Int>()

    // 初始值為0
    init{
        counter.value = 0
    }

    // 當觀察到LiveData有變動時，要改變資料
    fun guess(num: Int){
        // 避免counter為null
        var n = counter.value ?: 0
        n++
        counter.value = n
    }
}