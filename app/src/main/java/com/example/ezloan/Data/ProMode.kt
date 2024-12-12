package com.example.ezloan.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProMode(

    @PrimaryKey(autoGenerate = true)
    var isProMode: Boolean
)
