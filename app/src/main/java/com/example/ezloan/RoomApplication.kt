package com.example.ezloan

import android.app.Application
import androidx.room.Room
import com.example.ezloan.DB.TodoDatabase

class RoomApplication : Application() {

    companion object {
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
                            applicationContext,
                            TodoDatabase::class.java,
                            TodoDatabase.DATABASE_NAME
                        ).build()
    }
}