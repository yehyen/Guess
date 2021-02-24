package com.example.guess

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log

// IntentService比Service好用，因為會自動執行onDestroy
// 有自動排隊執行的特色：像聊天室訊息、下載等有順序性會好用
// onHandleIntent是自己額外一個執行緒
class CacheService() : IntentService("CacheService"){
    // 一個service覆寫一個onHandleIntent和一個onStartCommand時，預設會自動執行onStartCommand
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent")
    }

    private val TAG = CacheService::class.java.simpleName

    // 自行覆寫(command+o)下最列三項，在android.app.service之下
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    // onBind就是與activity有關聯
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}