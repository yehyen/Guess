package com.example.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
            "Snooker",
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

        //spinner微調器自製下拉選單 - 顯示固定個數的資料
        val colors = arrayOf("Red", "Green", "Blue")
        // 參數2指每一列資料的layout(這裡使用android內建的layout，可在SDK找到)，參數3.4指資料來源
        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, colors)
        // 下拉效果 - 選單變寬
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        // 設定到spinner的adapter
        spinner.adapter = adapter
        // 點擊後抓到資料，需override兩種方法：沒有點擊時，已經點擊時
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            // 參數1=AdapterView該對應的資料，參數2=點擊的TextView，參數4=id值
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d(TAG, "onItemSelected: ${colors[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
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
            5 -> startActivity(Intent(this, SnookerActivity::class.java))
            else -> return
        }

    }

    // 暫存被滑出功能表，定點在row_function.xml>name
    class FunctionHolder(view:View): RecyclerView.ViewHolder(view){
        var nameText: TextView = view.name
    }
}