package com.example.guess

import java.util.*

// 建立猜數字的特性
class SecretNumber{
    // 產生亂數1-10
    var secret = Random().nextInt(10) + 1
    // 會變動的次數
    var count = 0

    // 傳入要猜的值後，回傳此值與正解的相差多少
    fun validate(number: Int) : Int {
        count++
        return number - secret  // 若相差為0就是猜對了
    }

    //重置遊戲：secret重新產生，count歸0
    fun reset(){
        secret = Random().nextInt(10) + 1
        count = 0
    }

}