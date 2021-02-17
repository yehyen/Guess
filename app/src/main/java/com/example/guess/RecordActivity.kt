package com.example.guess

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.guess.data.GameDatabase
import com.example.guess.data.Record
import kotlinx.android.synthetic.main.activity_record.*

// 資料寫入儲存成檔案到記憶或硬碟，再將資料取出回傳
class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

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
            // 本身已有一個隱形執行緒要跟使用者互動
            // 這裡要另外創造一個執行緒來啟動才不會造成衝突
            // 當lambda物件中的參數是唯一或最後一個，可以放在()的外面
            Thread(){
                // 確保每一物件都用getInstance取得singleton單一物件來執行
                GameDatabase.getInstance(this)?.recordDao()?.
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
}