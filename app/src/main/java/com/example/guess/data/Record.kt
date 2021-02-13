package com.example.guess.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 要儲存所有猜數字的記錄(nickname, count)
// 標示annotation - Entity

@Entity
class Record(
    // 建構子參數
    @NonNull    // 給予參數標記：不能為空
    @ColumnInfo(name="nick")    // 縮短變數名稱
    var nickname:String,
    @NonNull
    var counter:Int){

    // 區域變數
    @PrimaryKey(autoGenerate = true)    // 自動變成Key值且在儲存時會自動加入
    var id: Long = 0
}

@Entity
class Word{
    @PrimaryKey
    var name: String = ""
    var means: String = ""
    var star: Int = 0
}