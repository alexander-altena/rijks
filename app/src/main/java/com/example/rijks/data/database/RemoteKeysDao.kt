package com.example.rijks.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE artObjectId = :artObjectId")
    suspend fun remoteKeysRepoId(artObjectId: String): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

}