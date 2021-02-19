package com.example.guess.data

import androidx.room.*

// data access object - interface(不用大刮號)
//room的標示語法Dao
@Dao
interface RecordDao{
    // 如果有重複資料insert就更新它
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: Record)

    // 資料查詢
    //加上關鍵字suspend就可以在coroutines執行
    @Query("select * from record")
    suspend fun getAll(): List<Record>


    // 刪除功能
    @Delete
    suspend fun delete(record: Record)
}