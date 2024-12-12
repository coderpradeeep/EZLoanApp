package com.example.ezloan.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ezloan.Data.ChatDataType
import com.example.ezloan.Data.ProMode


@Database(entities = [ChatDataType::class, ProMode::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "Todo_DB"
    }

    abstract fun getTodoDao() : TodoDao
}