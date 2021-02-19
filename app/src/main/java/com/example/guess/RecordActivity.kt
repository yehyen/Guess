package com.example.guess

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.guess.data.GameDatabase
import com.example.guess.data.Record
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// 資料寫入儲存成檔案到記憶或硬碟，再將資料取出回傳
class RecordActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    // 實作CoroutineScope，取得主要執行緒來源
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        // 產生工作
        job = Job()

        // 取得MaterialActivity的startActivityForResult()傳來資料(count數)
        // 參數1對應MaterialActivity中標籤COUNTER；參數2找不到標籤COUNTER值時，給予的預設值
        val count = intent.getIntExtra("COUNTER", -1)

        // 將得到的資料(count數)顯示在activity_record.xml
        counter.setText(count.toString())

        // 取得activity_record的save按鈕，按下按鈕後動作
        save.setOnClickListener{ view ->
            // 得到nickname
            val nick = nickname.text.toString()
            // 儲存資料容器，參數1檔案名稱，即將會儲存為xml檔，參數2存取權限
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                    .edit()     // 得到SharedPreferences編輯器，之後才能寫入資料
                    .putInt("REC_COUNTER", count)     // 寫入資料，參數1值的儲存名稱，參數2要寫入的資料
                    .putString("REC_NICKNAME", nick)
//                    .commit()   // commit()是存取與執行同步，一有資料存入立刻執行寫入硬碟，或有成功或失敗的返回值
                    // 儲存資料：apply()是存取與執行屬非同步，先將資料存取在記憶體後再執行(寫入硬碟)
                    // 讓後面的程式碼可在記憶體中修改前面資料，效率較好，沒有返回值
                    .apply()

            //insert to Room資料庫
            // Room test
            //Coroutines中的builder，自動在主要執行緒另外產生執行緒協程
            //此方式包含生命週期處理、清理
            launch{
                // 插入資料
                GameDatabase.getInstance(this@RecordActivity)?.recordDao()?.
                    insert(Record(nick, count))
            }.start()

            // 將nickname存入intent物件
            val intent = Intent()
            // 寫入資料，參數1值的標籤名稱，參數2要寫入的資料
            intent.putExtra("Nick", nick)

            // 在第此畫面呼叫setResult()，會回到MaterialActivity的onActivityResult()
            // 參數1指結果變數，參數2回傳資料
            setResult(RESULT_OK, intent)

            // 結束此activity，才會將RESULT_OK往前傳
            finish()

        }
    }

    // job工作要離開遊戲記錄清單之前，按下返回鍵會清除頁面資料
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}