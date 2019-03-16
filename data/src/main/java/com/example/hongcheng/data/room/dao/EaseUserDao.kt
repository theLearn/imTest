package com.example.hongcheng.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.hongcheng.data.room.entity.EaseUserInfoEntity

@Dao
interface EaseUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(data: List<EaseUserInfoEntity>) : List<Long>

    @Query("SELECT * FROM easeUser")
    fun getAll(): List<EaseUserInfoEntity>
}