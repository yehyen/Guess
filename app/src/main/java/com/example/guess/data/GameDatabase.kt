package com.example.guess.data

import androidx.room.Database
import androidx.room.RoomDatabase


// 可以讓使用者呼叫的抽象類別
// entities指此類別有什麼實體要儲存進去
// GameDatabase繼承RoomDatabase
@Database(entities = arrayOf(Record::class, Word::class), version=1)
abstract class GameDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDao
}