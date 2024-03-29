/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hongcheng.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.hongcheng.data.room.dao.EaseUserDao
import com.example.hongcheng.data.room.entity.EaseUserInfoEntity

/**
 * The Room database for this app
 */
@Database(entities = [EaseUserInfoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getEaseUserDao(): EaseUserDao

    companion object {
        const val DATABASE_NAME : String = "room_db"
        const val EASE_USER_TABLE_NAME : String  = "easeUser"
    }
}
