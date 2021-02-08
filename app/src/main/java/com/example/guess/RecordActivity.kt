package com.example.guess

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_record.*

// 資料儲存成檔案到硬碟
class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        // 取得前個Activity傳來資料(count數)
        // 參數1對應原Activity標籤；參數2找不到標籤COUNTER值時，給予的預設值
        val count = intent.getIntExtra("COUNTER", -1)

        // 將得到的資料(count數)顯示在activity_record.xml
        counter.setText(count.toString())

        // 取得activity_record的save按鈕，按下按鈕後動作
        save.setOnClickListener{ view ->
            // 得到nickname
            val nickname = nickname.text.toString()
            // 儲存資料容器，參數1檔案名稱，即將會儲存為xml檔，參數2存取權限
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                    .edit()     // 得到SharedPreferences編輯器，之後才能寫入資料
                    .putInt("REC_COUNTER", count)     // 寫入資料，參數1值的儲存名稱，參數2要寫入的資料
                    .putString("REC_NICKNAME", nickname)
//                    .commit()   // commit()是存取與執行同步，一有資料存入立刻執行寫入硬碟，或有成功或失敗的返回值
                    // 儲存資料：apply()是存取與執行屬非同步，先將資料存取在記憶體後再執行(寫入硬碟)
                    // 讓後面的程式碼可在記憶體中修改前面資料，效率較好，沒有返回值
                    .apply()

        }
    }
}