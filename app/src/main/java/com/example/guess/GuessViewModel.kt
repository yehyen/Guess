package com.example.guess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*


// 使用ViewModel必須要繼承ViewModel父類別
class GuessViewModel : ViewModel() {

    var count: Int = 0
    var secret: Int = 0

    // 建立counter的LiveData可變清單
    // 當Activity觀察counter有變動時，在Activity會得到通知而被執行
    val counter = MutableLiveData<Int>()
    // 提供給Activity傾聽觀察，result變動就知道其值為大為小
    val result = MutableLiveData<GameResult>()

    // 初始值
    init{
        counter.value = count
        // 直接產生亂數
        reset()
    }

    // 比對傳進來的值
    fun guess(num: Int){
        // 有num就猜過一次
        count++
        counter.value = count

        val gameResult = when(num - secret){
            0 -> GameResult.NUMBER_RIGHT   // 若相差為0就是猜對了
            in 1..Int.MAX_VALUE -> GameResult.SMALLER   // 若大於0就是猜的太大，提示要猜小一點
            else -> GameResult.BIGGER   // 提示要猜大一點
        }
        result.value = gameResult
    }

    //重置遊戲：secret重新產生，count歸0
    fun reset(){
        secret = Random().nextInt(10) + 1
        count = 0
        println(secret)
    }
}

// 提示視窗的三種狀態
enum class GameResult{
    BIGGER, SMALLER, NUMBER_RIGHT
}