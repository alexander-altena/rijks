package com.example.rijks.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "art_objects")
data class ArtObjectEntity(
    @PrimaryKey
    val id : String,
    val objectNumber : String,
    val title : String,
    val principalOrFirstMaker : String?,
    val imageUrl : String,
    val imgRatio: Float
)



