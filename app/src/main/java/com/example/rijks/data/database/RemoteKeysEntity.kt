package com.example.rijks.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    val artObjectId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
