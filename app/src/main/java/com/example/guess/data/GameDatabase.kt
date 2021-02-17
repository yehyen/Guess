package com.example.guess.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// 可以讓使用者呼叫的抽象類別
// entities指此類別有什麼實體要儲存進去
// GameDatabase繼承RoomDatabase
@Database(entities = arrayOf(Record::class, Word::class), version=1)
abstract class GameDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDao

    //若有兩件以上物件在同一時間要存取此class(gameDatabase)程式會出錯
    // singleton單一物件：static靜態類別，此類別確保同一時間只有一個物件在存取
    // 此類別裡面所有屬性方法都是單一個
    companion object {
        private var instance: GameDatabase? = null

        // 給外面呼叫，context=MaterialActivity
        fun getInstance(context: Context): GameDatabase?{
            // 若instance為null，表示沒有其它物件在存取才可以建立資料庫
            if(instance == null){
                instance = Room.databaseBuilder(context,
                    GameDatabase::class.java,
                    "game.db").build()
                }
            return instance
        }
    }
}