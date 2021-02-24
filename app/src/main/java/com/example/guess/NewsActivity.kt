package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // 將最新消息的fragment放入activity
        // beginTransaction()在改變畫面時，每一個交易動作都要成功，否則不會換畫面
        val fragTransaction = supportFragmentManager.beginTransaction()
        // 開始交易，參數2要加在哪個容器位置，參數3要加的Fragment物件
        fragTransaction.add(R.id.container, NewsFragment.instance)
        //
        fragTransaction.commit()
    }
}