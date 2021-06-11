package com.example.rijks.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtObjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(artObjectEntity: List<ArtObjectEntity>)

    @Query("SELECT * FROM art_objects")
    fun artObjects(): PagingSource<Int, ArtObjectEntity>

    @Query("DELETE FROM art_objects")
    suspend fun clearArtObjects()

}