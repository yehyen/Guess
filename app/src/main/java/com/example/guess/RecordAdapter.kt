package com.example.guess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guess.data.Record
import kotlinx.android.synthetic.main.row_record.view.*

// 連接遊戲記錄的功能清單，顯示在RecycleView可以上下滑動
// 繼承RecyclerView.Adapter就要實作onCreateViewHolder，onBindViewHolder，getItemCount
// 刮弧內若加val或var等於是直接當成屬性，若沒加就是傳遞參數
class RecordAdapter(var records: List<Record>): RecyclerView.Adapter<RecordViewHolder>(){

    //設定資料顯示畫面
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        // 資料超過RecordViewHolder的定點row_record.xml>nickname, counter
        return RecordViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.row_record, parent, false))
    }

    // 當有資料時會自動被呼叫，設定資料位置；position：資料在第幾列
    // 要顯示的暱稱、猜成功次數
    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.nickText.text = records.get(position).nickname
        holder.counterText.text = records.get(position).counter.toString() //得到是數字要轉成字串
    }

    // 裡面有多少筆資料，來自records
    override fun getItemCount(): Int {
        return records.size
    }
}

// 暫存被滑出功能表，定點在row_record.xml>nickname, counter
class RecordViewHolder(view: View): RecyclerView.ViewHolder(view){
    var nickText = view.record_nickname
    var counterText = view.record_counter
}