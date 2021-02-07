package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        // 取得前個Activity傳來資料(count數)
        // 參數1對應原Activity標籤；參數2找不到標籤COUNTER值時，給予的預設值
        val count = intent.getIntExtra("COUNTER", -1)

        // 將得到的資料(count數)顯示在activity_record.xml
        counter.setText(count.toString())
    }
}