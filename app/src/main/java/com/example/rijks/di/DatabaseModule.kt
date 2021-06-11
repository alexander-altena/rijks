package com.example.rijks.di

import android.content.Context
import androidx.room.Room
import com.example.rijks.data.database.RijksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRijksstudioDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, RijksDatabase::class.java, "rijks_db").build()

    @Provides
    @Singleton
    fun provideArtObjectsDao(db: RijksDatabase) = db.artObjectsDao()

    @Provides
    @Singleton
    fun provideKeysDao(db: RijksDatabase) = db.remoteKeysDao()
}