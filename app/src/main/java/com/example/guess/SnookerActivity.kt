package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guess.data.EventResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.coroutines.CoroutineContext

// 另一種Coroutines作法，直接繼承
// 需option + enter > implement members
class SnookerActivity : AppCompatActivity(), CoroutineScope {

    // static靜態類別
    companion object {
        val TAG = SnookerActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snooker)

        // Lifecycle傾聽觀察SnookerViewModel，取得資料筆數
        // 使用MVVM的LifeCycle和LifeData，以及ViewModel的建立
        // 可以避免多餘的工作留在背景佔盡資源
        // 如：進入ViewModel要耗時8秒但一進入後不到8秒就退出，正在進行產生的ViewModel會自動刪除不再繼續
        val viewModel = ViewModelProvider(this).get(SnookerViewModel::class.java)
        viewModel.events.observe(this, Observer { events ->
            Log.d(TAG, "onCreate: ${events.size}")
        })

        // 在底下已override Coroutines的get()，這裡可直接啟動Coroutines
        /*launch {
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
            val events = Gson().fromJson(data, EventResult::class.java)
            events.forEach {
                Log.d(TAG, "onCreate: $it")
            }
        }*/

        // 利用Corutine連線網路工作
/*        CoroutineScope(Dispatchers.IO).launch {
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
            val events = Gson().fromJson(data, EventResult::class.java)
            events.forEach {
                Log.d(TAG, "onCreate: $it")
            }
        }*/
    }

    // 網路工作不能在Main執行緒動作
    override val coroutineContext: CoroutineContext
//        get() = Job() + Dispatchers.Main 連到Main不對
        get() = Job() + Dispatchers.IO
}