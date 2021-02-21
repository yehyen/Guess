package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.guess.data.EventResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class SnookerActivity : AppCompatActivity() {

    val TAG = SnookerActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snooker)

        // 利用Corutine連線網路工作
        CoroutineScope(Dispatchers.IO).launch {
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
            val events = Gson().fromJson(data, EventResult::class.java)
            events.forEach {
                Log.d(TAG, "onCreate: $it")
            }

        }
    }
}