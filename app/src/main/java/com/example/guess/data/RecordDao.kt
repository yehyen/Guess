package com.example.guess.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// data access object - interface(不用大刮號)
//room的標示語法Dao
@Dao
interface RecordDao{
    // 如果有重複資料insert就更新它
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: Record)

    // 資料查詢
    //加上關鍵字suspend就可以在coroutines執行
    @Query("select * from record")
    suspend fun getAll(): List<Record>
}