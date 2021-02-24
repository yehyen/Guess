package com.example.guess

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

// Service用於在後臺完成使用者指定的操作
// service有兩類，一類為自生自滅，執行完就結束；另一類與activity有關聯
class CacheService() : Service(){

    private val TAG = CacheService::class.java.simpleName

    // 自行覆寫(command+o)下最列三項，在android.app.service之下
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        // 假設severice執行後，android系統因為資源短缺而把service殺掉時，自己在把自己生出來
        return START_STICKY
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