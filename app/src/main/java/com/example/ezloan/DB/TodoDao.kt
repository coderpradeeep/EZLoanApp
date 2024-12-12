package com.example.ezloan.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ezloan.Data.ChatDataType
import com.example.ezloan.Data.ProMode

@Dao
interface TodoDao {

    @Query("SELECT * FROM ChatDataType")
    fun getAllChat() : LiveData<List<ChatDataType>>

    @Insert
    fun insertChat(chat: ChatDataType)

    @Query("DELETE FROM ChatDataType")
    fun deleteMyChatList()

    @Insert
    fun insertProModeStatus(proMode: ProMode)

    @Query("SELECT * FROM ProMode")
    fun getProModeStatus() : LiveData<Boolean>
}