package com.example.guess

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guess.data.GameDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.android.synthetic.main.activity_record_list.recycler

// 遊戲記錄清單
class RecordListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)

        // 利用AsynTask.execute在另一個執行緒動作
        AsyncTask.execute{

            // 從資料庫GameDatabase,Record,RecordDao得到資料
            val records = GameDatabase.getInstance(this)?.recordDao()?.getAll()

            //RecyclerView：當user上下滑動元件離開畫面時，該元件會先被儲存等待之後被取出
            //用let?{}使records可為null，不然會出錯，並判斷records是否為null
            // record若是null傳進去沒有意義，若不為null就會執行區塊動作
            records?.let{
                // runOnUiThread{}可不加，一般UI元件執行緒不能在AsyncTask.execute執行
                // 讓recycler固定在runOnUiThread{}執行
                runOnUiThread{
                    //設定版面功能表：LinearLayout清單流水式，可以上下滑動
                    recycler.layoutManager = LinearLayoutManager(this)
                    // 是否固定大小
                    recycler.setHasFixedSize(true)
                    // adapter連接器，實現一個或多個系統不同部分之間的連接，獲得協同處理
                    // it=records
                    recycler.adapter = RecordAdapter(it)
                }
            }
        }
    }
}