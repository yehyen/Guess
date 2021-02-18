package com.example.guess

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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*

class MainActivity : AppCompatActivity() {

    // 得到MainActivity的字串，讓Log除錯用
    val TAG = MainActivity::class.java.simpleName

    //顯示項目
    val functions = listOf<String>(
            "Camera",
            "Invite friend",
            "Parking",
            "Download coupons",
            "A",
            "B",
            "B",
            "B",
            "B",
            "B",
            "B",
            "B",
            "News",
            "Maps")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        // 當有資料時，設定資料位置；position：資料在第幾列
        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functions.get(position)
        }

        // 裡面有多少筆資料
        override fun getItemCount(): Int {
            return functions.size
        }
    }

    // 暫存功能
    class FunctionHolder(view:View): RecyclerView.ViewHolder(view){
        var nameText: TextView = view.name
    }
}