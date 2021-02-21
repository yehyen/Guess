package com.example.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guess.data.EventResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import org.json.JSONArray
import java.net.URL

// 主功能設計
class MainActivity : AppCompatActivity() {

    // 得到MainActivity的字串，讓Log除錯用
    val TAG = MainActivity::class.java.simpleName

    //顯示項目
    val functions = listOf<String>(
            "Camera",
            "Guess game",
            "Record list",
            "Download coupons",
            "News",
            "Maps")

    // mainthread
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()

            // 利用Gson()快速解析json資料
            val result = Gson().fromJson(data, EventResult::class.java)
            // 得到所有json資料
            result.forEach {
                Log.d(TAG, "onCreate: $it")
            }

        }.start()


        //RecyclerView：當user上下滑動元件離開畫面時，該元件會先被儲存等待之後被取出
        //早期是用丟棄方式
        //設定版面功能表：LinearLayout清單流水式，可以上下滑動
        recycler.layoutManager = LinearLayoutManager(this)
        // 是否固定大小
        recycler.setHasFixedSize(true)
        // adapter連接器，實現一個或多個系統不同部分之間的連接，獲得協同處理
        recycler.adapter = FunctionAdapter()
    }

    //配置畫面的holder，必須繼承RecyclerView.Adapter來做畫面功能
    //內部類別，只能給MainActivity用
    inner class FunctionAdapter(): RecyclerView.Adapter<FunctionHolder>(){

        //設定資料顯示畫面
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_function, parent, false)
            val holder = FunctionHolder(view)
            return holder
        }

        // 當有資料時會自動被呼叫，設定資料位置；position：資料在第幾列
        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functions.get(position)
            // 當user按下功能鍵，傳入被按下的position
            holder.itemView.setOnClickListener {
                functionClicked(position)
            }
        }

        // 裡面有多少筆資料，來自functions
        override fun getItemCount(): Int {
            return functions.size
        }
    }

    // 判斷功能鍵，按下按鈕跳到對應的該畫面
    private fun functionClicked(position: Int) {
        when(position){
            1 -> startActivity(Intent(this, MaterialActivity::class.java))
            2 -> startActivity(Intent(this, RecordListActivity::class.java))
            else -> return
        }

    }

    // 暫存被滑出功能表，定點在row_function.xml>name
    class FunctionHolder(view:View): RecyclerView.ViewHolder(view){
        var nameText: TextView = view.name
    }
}