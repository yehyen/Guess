package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // 產生猜數字物件
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "secret:" + secretNumber.secret)

    }

    // 與onClick事件互動，button>CommonAttributes>onClick連動，onClick名稱與fun名稱要相同
    // 參數型態為View類別，即在Acivity上所顯示的控件
    fun check(view: View){

        //取得EditText輸入後得到的數字，id=number，使用屬性text取得內容後用toString()得到字串，最後再轉成toInt()
        val n = number.text.toString().toInt()
        println("number: $n")

        // 訊息處理Log類別，用.d方便除錯記錄，參數1指來自class名稱，參數2指出現的內容
        Log.d("MainActivity", "number:" + n)

        // 與使用者互動，輸入值來得到提示
        val diff = secretNumber.validate(n)
        var message = "Yes, you got it"
        if(diff < 0){
            message = "Bigger"
        }else if(diff > 0){
            message = "Smaller"
        }

        //提示內容Toast類別：浮動元件
        // makeText()參數1需放入Context類別，MainActivity有繼承到Context類別
        // this代表MainActivity物件
        // 參數2指給使用者看的內容
        // 參數3指持續時間LENGTH_LONG=1
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}