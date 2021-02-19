package com.example.guess

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guess.data.GameDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.android.synthetic.main.activity_record_list.recycler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// 遊戲記錄清單
class RecordListActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    // 實作CoroutineScope，取得主要執行緒來源
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)

        // 產生工作
        job = Job()

        //Coroutines中的builder，自動在主要執行緒另外產生執行緒協程
        //此方式包含生命週期處理、清理
        launch {
            val records = GameDatabase.getInstance(this@RecordListActivity)?.recordDao()?.getAll()
            records?.let{
                //設定版面功能表：LinearLayout清單流水式，可以上下滑動
                recycler.layoutManager = LinearLayoutManager(this@RecordListActivity)
                // 是否固定大小
                recycler.setHasFixedSize(true)
                // adapter連接器，實現一個或多個系統不同部分之間的連接，獲得協同處理
                // it=records
                recycler.adapter = RecordAdapter(it)
            }
        }
    }

    // job工作要離開遊戲記錄清單之前，按下返回鍵會清除頁面資料
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}