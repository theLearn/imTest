package com.example.hongcheng.data.room.entity

import android.arch.persistence.room.Entity
import com.example.hongcheng.data.room.AppDatabase

@Entity(tableName = AppDatabase.EASE_USER_TABLE_NAME,
        primaryKeys = ["userNo"])
data class EaseUserInfoEntity(var userNo: String = "",
                              var photo: String = "",
                              var name: String = "")