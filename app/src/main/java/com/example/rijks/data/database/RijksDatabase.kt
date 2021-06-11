package com.example.rijks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArtObjectEntity::class, RemoteKeysEntity::class],
    version = 1
)
abstract class RijksDatabase : RoomDatabase(){

    abstract fun artObjectsDao(): ArtObjectDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}